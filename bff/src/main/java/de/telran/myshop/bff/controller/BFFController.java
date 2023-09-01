package de.telran.myshop.bff.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.configurationprocessor.json.JSONException;
import org.springframework.boot.configurationprocessor.json.JSONObject;
import org.springframework.http.*;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;
import de.telran.myshop.bff.dto.DataResult;
import de.telran.myshop.bff.dto.Operation;
import de.telran.myshop.bff.dto.SearchValues;
import de.telran.myshop.bff.dto.UserProfile;
import de.telran.myshop.bff.utils.CookieUtils;

import java.util.Base64;
import java.util.HashMap;
import java.util.Map;

/*
Adapter between frontend and resource server - redirects requests between them
Main task - to store tokens in secure cookies
Abbreviations:
AT - access token
RT - refresh token
IT - id token
RS - resource server
KC - Keycloak
 */

@RestController
@RequestMapping("/bff") // Base URI
public class BFFController {

    // WebClient can also be used instead of RestTemplate for asynchronous requests
    private static final RestTemplate restTemplate = new RestTemplate(); // For making web requests to KeyCloak

    // Keys for cookie names
    public static final String IDTOKEN_COOKIE_KEY = "IT";
    public static final String REFRESHTOKEN_COOKIE_KEY = "RT";
    public static final String ACCESSTOKEN_COOKIE_KEY = "AT";

    // Values from configuration (values are automatically injected)
    @Value("${keycloak.secret}")
    private String clientSecret;

    @Value("${resourceserver.url}")
    private String resourceServerURL;

    @Value("${keycloak.url}")
    private String keyCloakURI;

    @Value("${client.url}")
    private String clientURL;

    @Value("${keycloak.clientid}")
    private String clientId;

    @Value("${keycloak.granttype.code}")
    private String grantTypeCode;

    @Value("${keycloak.granttype.refresh}")
    private String grantTypeRefresh;

    @Value("${app.log.level}")
    private String logLevel;

    // Utility class for working with cookies
    private final CookieUtils cookieUtils;

    // Cookie values
    private int accessTokenDuration;
    private int refreshTokenDuration;

    private String accessToken;
    private String idToken;
    private String refreshToken;

    // Used to extract any user values from JSON
    private JSONObject payload;

    // User identifier from KC, can be used for searching
    private String userId;


    @Autowired
    public BFFController(CookieUtils cookieUtils) {
        this.cookieUtils = cookieUtils;
    }


    // Redirects the request to the Resource Server and adds the access token to it,
    // cookies will be automatically passed by the browser based on the domain name
    // Handles the request by redirecting it to the Resource Server and adding the access token.
// Example usage: any search criteria can be passed here.
    @PostMapping("/data")
    public ResponseEntity<DataResult> operation(@RequestBody SearchValues searchValues, @CookieValue("AT") String accessToken) {

        // As an example - you can pass any search criteria here.
        System.out.println("searchText = " + searchValues.getSearchText());

        // Authorization header with the access token
        HttpHeaders headers = new HttpHeaders();
        headers.setBearerAuth(accessToken); // The word "Bearer" will be added automatically
        headers.setContentType(MediaType.APPLICATION_JSON); // To send searchValues in JSON format

        // Special container for sending an object within the request
        HttpEntity<SearchValues> request = new HttpEntity<>(searchValues, headers);

        // Fetching user business data (the response will be wrapped in DataResult)
        ResponseEntity<DataResult> response = restTemplate.postForEntity(resourceServerURL + "/product/test", request, DataResult.class);

        return response;
    }


    // Universal method that redirects any frontend request to the Resource Server and adds a token from the cookie
    @PostMapping("/operation")
    public ResponseEntity<Object> operation(@RequestBody Operation operation, @CookieValue("AT") String accessToken) {

        // Authorization header with the access token
        HttpHeaders headers = new HttpHeaders();
        headers.setBearerAuth(accessToken); // The word "Bearer" will be added automatically
        headers.setContentType(MediaType.APPLICATION_JSON); // To send data in JSON format

        HttpEntity<Object> request;

        // Special container for sending an object within the request
        if (operation.getBody() != null) {
            request = new HttpEntity<>(operation.getBody(), headers);
        } else {
            request = new HttpEntity<>(headers);
        }

        try {
            // Calling a method on the backend through BFF
            if (logLevel.equals("debug")) {
                System.out.println("Url=" + operation.getUrl());
                System.out.println("Http Method=" + operation.getHttpMethod());
                System.out.println("Request: " + request);
            }

            ResponseEntity<Object> response = restTemplate.exchange(operation.getUrl(), operation.getHttpMethod(), request, Object.class);

            // If the request is successful, simply return the backend response
            return response;
        } catch (HttpClientErrorException ex) {
            // Handling a 4xx error received from the backend
            return ResponseEntity.status(ex.getRawStatusCode())
                    .body(ex.getResponseBodyAsString());
        } catch (Exception ex) {
            // Logic for handling other errors
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(ex.getMessage());
        }
    }



    // Obtaining new tokens based on the old refresh token
    @GetMapping("/exchange")
    public ResponseEntity<String> exchangeRefreshToken(@CookieValue("RT") String oldRefreshToken) {

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);

        // Request parameters (in key-value format)
        MultiValueMap<String, String> mapForm = new LinkedMultiValueMap<>();
        mapForm.add("grant_type", grantTypeRefresh);
        mapForm.add("client_id", clientId);
        mapForm.add("client_secret", clientSecret);
        mapForm.add("refresh_token", oldRefreshToken);

        // Creating a request to be executed
        HttpEntity<MultiValueMap<String, String>> request = new HttpEntity<>(mapForm, headers);

        // Executing the request (various methods can be used, not just exchange)
        ResponseEntity<String> response = restTemplate.exchange(keyCloakURI + "/token", HttpMethod.POST, request, String.class);

        // Creating cookies for storing in the browser (frontend)
        HttpHeaders responseHeaders = createCookies();

        // Sending the client a response with all the cookies (which will be automatically stored in the browser)
        // Cookie values with new tokens will overwrite in the browser
        return ResponseEntity.ok().headers(responseHeaders).build();
    }



    // Obtaining detailed user profile data
    // All data is taken from the previously received idToken
    // No request is made to the RS here, as business data is not requested here
    @GetMapping("/profile")
    public ResponseEntity<UserProfile> profile() {

        userId = getPayloadValue("sid");

        UserProfile userProfile = new UserProfile(
                getPayloadValue("preferred_username"),
                getPayloadValue("name"),
                getPayloadValue("email"),
                userId
        );

        return ResponseEntity.ok(userProfile);
    }

    // Logging out the user's sessions within KeyCloak and also clearing all cookies
    @GetMapping("/logout")
    public ResponseEntity<String> logout(@CookieValue("IT") String idToken) {

        // 1. Close sessions in KeyCloak for the given user
        // 2. Clear cookies in the browser

        // To correctly execute a GET request with parameters, we use the UriComponentsBuilder class
        String urlTemplate = UriComponentsBuilder.fromHttpUrl(keyCloakURI + "/logout")
                .queryParam("post_logout_redirect_uri", "{post_logout_redirect_uri}")
                .queryParam("id_token_hint", "{id_token_hint}")
                .queryParam("client_id", "{client_id}")
                .encode()
                .toUriString();

        // Specific values that will be substituted into the GET request parameters
        Map<String, String> params = new HashMap<>();
        params.put("post_logout_redirect_uri", clientURL); // Can be any, as frontend receives a response from BFF, not directly from Auth Server
        params.put("id_token_hint", idToken); // idToken tells Auth Server for whom we want to "log out"
        params.put("client_id", clientId);

        // Execute the request (we don't need the result)
        restTemplate.getForEntity(
                urlTemplate, // GET request template - values from params will be substituted there
                String.class, // We don't expect any response, only status, so we can specify String
                params // Which values will be substituted into the GET request template
        );

        // Clear the values and expiration times of all cookies (the browser will delete them automatically)
        HttpHeaders responseHeaders = clearCookies();

        // Send the browser a response with empty cookies to clear (zero out) them, as the user has logged out
        return ResponseEntity.ok().headers(responseHeaders).build();
    }



    // Obtaining all tokens and storing them in cookies
// The tokens themselves will not be stored in the browser, only passed in cookies
// This way, they won't be accessible from browser code (protection against XSS attacks)
    @PostMapping("/token")
    public ResponseEntity<String> token(@RequestBody String code) {// Receive auth code to exchange for tokens
        System.out.println("Inside token method");
        System.out.println("Received body: " + code);
        // 1. Exchange auth code for tokens
        // 2. Store tokens in secure cookies

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);

        // Request parameters
        MultiValueMap<String, String> mapForm = new LinkedMultiValueMap<>();
        mapForm.add("grant_type", grantTypeCode);
        mapForm.add("client_id", clientId);
        mapForm.add("client_secret", clientSecret); // Using a static secret (can be securely stored) instead of code verifier from PKCE
        mapForm.add("code", code);

        // In the case of client working through BFF, this redirect_uri can be anything, as we're not manually opening a window, so there won't be an automatic redirect to redirect_uri
        // The client receives the response in a ResponseEntity object
        // HOWEVER! The value still needs to be passed, otherwise the grant type won't work and there will be an error.
        // The value must necessarily be with the client's address and port, for example, https://localhost:8080, otherwise there will be an "Incorrect redirect_uri" error, because initially the authorization request was made from the client's address
        mapForm.add("redirect_uri", clientURL);

        // Adding headers and parameters to the request
        HttpEntity<MultiValueMap<String, String>> request = new HttpEntity<>(mapForm, headers);

        // Execute the request
        ResponseEntity<String> response = restTemplate.exchange(keyCloakURI + "/token", HttpMethod.POST, request, String.class);
        // We receive JSON in text form

        parseResponse(response); // Get all the necessary fields from the KC response

        // Read data from JSON and store in cookies
        HttpHeaders responseHeaders = createCookies();

        // Send the client user data (and JWT cookies in the Set-Cookie header)
        return ResponseEntity.ok().headers(responseHeaders).build();
    }

    // Get any claim value from the payload
    private String getPayloadValue(String claim) {
        try {
            return payload.getString(claim);
        } catch (JSONException e) {
            throw new RuntimeException(e);
        }
    }


    // Extracting the required fields from the KC response
    private void parseResponse(ResponseEntity<String> response) {

        // JSON parser
        ObjectMapper mapper = new ObjectMapper();

        // First, get the root JSON element
        try {
            JsonNode root = mapper.readTree(response.getBody());

            // Get token values from the root JSON element in Base64 format
            accessToken = root.get("access_token").asText();
            idToken = root.get("id_token").asText();
            refreshToken = root.get("refresh_token").asText();

            // Token expiration times are also taken from the JSON
            // Cookies will become inactive at the same time as the tokens expire in KeyCloak
            accessTokenDuration = root.get("expires_in").asInt();
            refreshTokenDuration = root.get("refresh_expires_in").asInt();

            // All user data (profile)
            String payloadPart = idToken.split("\\.")[1]; // Take the value of the payload section in Base64 format
            String payloadStr = new String(Base64.getUrlDecoder().decode(payloadPart)); // Decode from Base64 to plain JSON text
            payload = new JSONObject(payloadStr); // Form a convenient JSON format - now you can retrieve any fields from it

        } catch (JsonProcessingException | JSONException e) {
            throw new RuntimeException(e);
        }

    }

    // Creating cookies for the response
    private HttpHeaders createCookies() {

        // Create cookies that the browser will automatically send to BFF with each request
        HttpCookie accessTokenCookie = cookieUtils.createCookie(ACCESSTOKEN_COOKIE_KEY, accessToken, accessTokenDuration);
        HttpCookie refreshTokenCookie = cookieUtils.createCookie(REFRESHTOKEN_COOKIE_KEY, refreshToken, refreshTokenDuration);
        HttpCookie idTokenCookie = cookieUtils.createCookie(IDTOKEN_COOKIE_KEY, idToken, accessTokenDuration); // Set the same duration as AT

        // To make the browser apply cookies to the browser - specify them in the Set-Cookie header in the response
        HttpHeaders responseHeaders = new HttpHeaders();
        responseHeaders.add(HttpHeaders.SET_COOKIE, accessTokenCookie.toString());
        responseHeaders.add(HttpHeaders.SET_COOKIE, refreshTokenCookie.toString());
        responseHeaders.add(HttpHeaders.SET_COOKIE, idTokenCookie.toString());

        return responseHeaders;
    }

    // Clearing all cookies so that the browser deletes them
    private HttpHeaders clearCookies() {
        // Nullify cookies that we send back to the client in the response, then the browser will automatically delete them
        HttpCookie accessTokenCookie = cookieUtils.deleteCookie(ACCESSTOKEN_COOKIE_KEY);
        HttpCookie refreshTokenCookie = cookieUtils.deleteCookie(REFRESHTOKEN_COOKIE_KEY);
        HttpCookie idTokenCookie = cookieUtils.deleteCookie(IDTOKEN_COOKIE_KEY);

        // To make the browser apply cookies to the browser - specify them in the Set-Cookie header in the response
        HttpHeaders responseHeaders = new HttpHeaders();
        responseHeaders.add(HttpHeaders.SET_COOKIE, accessTokenCookie.toString());
        responseHeaders.add(HttpHeaders.SET_COOKIE, refreshTokenCookie.toString());
        responseHeaders.add(HttpHeaders.SET_COOKIE, idTokenCookie.toString());
        return responseHeaders;
    }


}

