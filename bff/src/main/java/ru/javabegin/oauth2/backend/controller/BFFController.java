package ru.javabegin.oauth2.backend.controller;

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
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;
import ru.javabegin.oauth2.backend.dto.DataResult;
import ru.javabegin.oauth2.backend.dto.Operation;
import ru.javabegin.oauth2.backend.dto.SearchValues;
import ru.javabegin.oauth2.backend.dto.UserProfile;
import ru.javabegin.oauth2.backend.utils.CookieUtils;

import java.util.Base64;
import java.util.HashMap;
import java.util.Map;

/*
Адаптер между frontend и resource server - перенаправляет запросы между ними
Основная задача - сохранять токены в безопасных куках

Сокращения:
AT - access token
RT - refresh token
IT - id token
RS - resource server
KC - keycloak

 */

@RestController
@RequestMapping("/bff") // базовый URI
public class BFFController {

    // можно также использовать WebClient вместо RestTemplate, если нужны асинхронные запросы
    private static final RestTemplate restTemplate = new RestTemplate(); // для выполнения веб запросов на KeyCloak

    // ключи для названий куков
    public static final String IDTOKEN_COOKIE_KEY = "IT";
    public static final String REFRESHTOKEN_COOKIE_KEY = "RT";
    public static final String ACCESSTOKEN_COOKIE_KEY = "AT";

    // значения из настроек (значения внедряются автоматически)
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

    // класс-утилита для работы с куками
    private final CookieUtils cookieUtils;

    // значения куков
    private int accessTokenDuration;
    private int refreshTokenDuration;

    private String accessToken;
    private String idToken;
    private String refreshToken;

    // используется, чтобы получать любые значения пользователя из JSON
    private JSONObject payload;

    // идентификатор пользователя из KC, может использоваться для поиска
    private String userId;


    @Autowired
    public BFFController(CookieUtils cookieUtils) { // внедряем объекты
        this.cookieUtils = cookieUtils;
    }


    // перенаправляет запрос в Resource Server и добавляет в него access token,
    // куки передадутся автоматически браузером по имени домена
    @PostMapping("/data")
    public ResponseEntity<DataResult> data(@RequestBody SearchValues searchValues, @CookieValue("AT") String accessToken) {

        // как пример - сюда можно передавать любые критерии поиска
        System.out.println("searchText = " + searchValues.getSearchText());

        // заголовок авторизации с access token
        HttpHeaders headers = new HttpHeaders();
        headers.setBearerAuth(accessToken); // слово Bearer будет добавлено автоматически
        headers.setContentType(MediaType.APPLICATION_JSON); // чтобы передать searchValues в формате JSON

        // специальный контейнер для передачи объекта внутри запроса
        HttpEntity<SearchValues> request = new HttpEntity<>(searchValues, headers);

        // получение бизнес-данных пользователя (ответ обернется в DataResult)
        ResponseEntity<DataResult> response = restTemplate.postForEntity(resourceServerURL + "/user/data", request, DataResult.class);

        return response;
    }


    // получение новых токенов на основе старого RT
    @GetMapping("/exchange")
    public ResponseEntity<String> exchangeRefreshToken(@CookieValue("RT") String oldRefreshToken) {

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);

        // параметры запроса (в формате ключ-значение)
        MultiValueMap<String, String> mapForm = new LinkedMultiValueMap<>();
        mapForm.add("grant_type", grantTypeRefresh);
        mapForm.add("client_id", clientId);
        mapForm.add("client_secret", clientSecret);
        mapForm.add("refresh_token", oldRefreshToken);

        // собираем запрос для выполнения
        HttpEntity<MultiValueMap<String, String>> request = new HttpEntity<>(mapForm, headers);

        // выполняем запрос (можно применять разные методы, не только exchange)
        ResponseEntity<String> response = restTemplate.exchange(keyCloakURI + "/token", HttpMethod.POST, request, String.class);


        // создаем куки для их записи в браузер (frontend)
        HttpHeaders responseHeaders = createCookies();

        // отправляем клиенту ответ со всеми куками (которые запишутся в браузер автоматически)
        // значения куков с новыми токенами перезапишутся в браузер
        return ResponseEntity.ok().headers(responseHeaders).build();


    }


    // получение подробных данных пользователя (профайл)
    // все данные берем из ранее полученного idToken
    // запроса в RS не делаем, т.к. бизнес-данные тут не запрашиваются
    @GetMapping("/profile")
    public ResponseEntity<UserProfile> profile() {

        userId = getPayloadValue("sid");

        UserProfile userProfile = new UserProfile(

                getPayloadValue("phone2"),
                getPayloadValue("name"),
                getPayloadValue("email"),
                userId
        );

        return ResponseEntity.ok(userProfile);

    }


    // удаление сессий пользователя внутри KeyCloak и также зануление всех куков
    @GetMapping("/logout")
    public ResponseEntity<String> logout(@CookieValue("IT") String idToken) {

        // 1. закрыть сессии в KeyCloak для данного пользователя
        // 2. занулить куки в браузере

        // чтобы корректно выполнить GET запрос с параметрами - применяем класс UriComponentsBuilder
        String urlTemplate = UriComponentsBuilder.fromHttpUrl(keyCloakURI + "/logout")
                .queryParam("post_logout_redirect_uri", "{post_logout_redirect_uri}")
                .queryParam("id_token_hint", "{id_token_hint}")
                .queryParam("client_id", "{client_id}")
                .encode()
                .toUriString();

        // конкретные значения, которые будут подставлены в параметры GET запроса
        Map<String, String> params = new HashMap<>();
        params.put("post_logout_redirect_uri", clientURL); // может быть любым, т.к. frontend получает ответ от BFF, а не напрямую от Auth Server
        params.put("id_token_hint", idToken); // idToken указывает Auth Server, для кого мы хотим "выйти"
        params.put("client_id", clientId);

        // выполняем запрос (результат нам не нужен)
        restTemplate.getForEntity(
                urlTemplate, // шаблон GET запроса - туда будут подставляться значения из params
                String.class, // нам ничего не возвращается в ответе, только статус, поэтому можно указать String
                params // какие значения будут подставлены в шаблон GET запроса
        );


        // занулить значения и сроки годности всех куков (тогда браузер их удалит автоматически)
        HttpHeaders responseHeaders = clearCookies();

        // отправляем браузеру ответ с пустыми куками для их удаления (зануления), т.к. пользователь вышел из системы
        return ResponseEntity.ok().headers(responseHeaders).build();

    }


    // получение всех токенов и запись в куки
    // сами токены сохраняться в браузере не будут, а только будут передаваться в куках
    // таким образом к ним не будет доступа из кода браузера (защита от XSS атак)
    @PostMapping("/token")
    public ResponseEntity<String> token(@RequestBody String code) {// получаем auth code, чтобы обменять его на токены

        // 1. обменять auth code на токены
        // 2. сохранить токены в защищенные куки


        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);

        // параметры запроса
        MultiValueMap<String, String> mapForm = new LinkedMultiValueMap<>();
        mapForm.add("grant_type", grantTypeCode);
        mapForm.add("client_id", clientId);
        mapForm.add("client_secret", clientSecret); // используем статичный секрет (можем его хранить безопасно), вместо code verifier из PKCE
        mapForm.add("code", code);

        // В случае работы клиента через BFF - этот redirect_uri может быть любым, т.к. мы не открываем окно вручную, а значит не будет автоматического перехода в redirect_uri
        // Клиент получает ответ в объекте ResponseEntity
        // НО! Значение все равно передавать нужно, без этого grant type не сработает и будет ошибка.
        // Значение обязательно должно быть с адресом и портом клиента, например https://localhost:8080  иначе будет ошибка Incorrect redirect_uri, потому что изначально запрос на авторизацию выполнялся именно с адреса клиента
        mapForm.add("redirect_uri", clientURL);

        // добавляем в запрос заголовки и параметры
        HttpEntity<MultiValueMap<String, String>> request = new HttpEntity<>(mapForm, headers);

        // выполняем запрос
        ResponseEntity<String> response = restTemplate.exchange(keyCloakURI + "/token", HttpMethod.POST, request, String.class);
        // мы получаем JSON в виде текста

        parseResponse(response); // получить все нужные поля ответа KC

        // считать данные из JSON и записать в куки
        HttpHeaders responseHeaders = createCookies();

        // отправляем клиенту данные пользователя (и jwt-кук в заголовке Set-Cookie)
        return ResponseEntity.ok().headers(responseHeaders).build();


    }

    // универсальный метод, который перенаправляет любой запрос из frontend на Resource Server и добавляет в него токен из кука
    @PostMapping("/operation")
    public ResponseEntity<Object> data(@RequestBody Operation operation, @CookieValue("AT") String accessToken) {

        // заголовок авторизации с access token
        HttpHeaders headers = new HttpHeaders();
        headers.setBearerAuth(accessToken); // слово Bearer будет добавлено автоматически
        headers.setContentType(MediaType.APPLICATION_JSON); // чтобы передать searchValues в формате JSON

        HttpEntity<Object> request;

        // специальный контейнер для передачи объекта внутри запроса
        if (operation.getBody() != null) {
            request = new HttpEntity<>(operation.getBody(), headers);
        }else {
            request = new HttpEntity<>(headers);
        }

        // получение бизнес-данных пользователя (ответ обернется в DataResult)
        ResponseEntity<Object> response = restTemplate.exchange(operation.getUrl(), operation.getHttpMethod(), request, Object.class);

        return response;
    }




    // получить любое значение claim из payload
    private String getPayloadValue(String claim) {
        try {
            return payload.getString(claim);
        } catch (JSONException e) {
            throw new RuntimeException(e);
        }
    }

    // получение нужных полей из ответа KC
    private void parseResponse(ResponseEntity<String> response) {

        // парсер JSON
        ObjectMapper mapper = new ObjectMapper();

        // сначала нужно получить корневой элемент JSON
        try {
            JsonNode root = mapper.readTree(response.getBody());

            // получаем значения токенов из корневого элемента JSON в формате Base64
            accessToken = root.get("access_token").asText();
            idToken = root.get("id_token").asText();
            refreshToken = root.get("refresh_token").asText();

            // Сроки действия для токенов берем также из JSON
            // Куки станут неактивные в то же время, как выйдет срок действия токенов в KeyCloak
            accessTokenDuration = root.get("expires_in").asInt();
            refreshTokenDuration = root.get("refresh_expires_in").asInt();

            // все данные пользователя (профайл)
            String payloadPart = idToken.split("\\.")[1]; // берем значение раздела payload в формате Base64
            String payloadStr = new String(Base64.getUrlDecoder().decode(payloadPart)); // декодируем из Base64 в обычный текст JSON
            payload = new JSONObject(payloadStr); // формируем удобный формат JSON - из него теперь можно получать любе поля

        } catch (JsonProcessingException | JSONException e) {
            throw new RuntimeException(e);
        }

    }

    // создание куков для response
    private HttpHeaders createCookies() {

        // создаем куки, которые браузер будет отправлять автоматически на BFF при каждом запросе
        HttpCookie accessTokenCookie = cookieUtils.createCookie(ACCESSTOKEN_COOKIE_KEY, accessToken, accessTokenDuration);
        HttpCookie refreshTokenCookie = cookieUtils.createCookie(REFRESHTOKEN_COOKIE_KEY, refreshToken, refreshTokenDuration);
        HttpCookie idTokenCookie = cookieUtils.createCookie(IDTOKEN_COOKIE_KEY, idToken, accessTokenDuration); // задаем такой же срок, что и AT

        // чтобы браузер применил куки к бразуеру - указываем их в заголовке Set-Cookie в response
        HttpHeaders responseHeaders = new HttpHeaders();
        responseHeaders.add(HttpHeaders.SET_COOKIE, accessTokenCookie.toString());
        responseHeaders.add(HttpHeaders.SET_COOKIE, refreshTokenCookie.toString());
        responseHeaders.add(HttpHeaders.SET_COOKIE, idTokenCookie.toString());

        return responseHeaders;
    }


    // зануляет все куки, чтобы браузер их удалил у себя
    private HttpHeaders clearCookies() {
        // зануляем куки, которые отправляем обратно клиенту в response, тогда браузер автоматически удалит их
        HttpCookie accessTokenCookie = cookieUtils.deleteCookie(ACCESSTOKEN_COOKIE_KEY);
        HttpCookie refreshTokenCookie = cookieUtils.deleteCookie(REFRESHTOKEN_COOKIE_KEY);
        HttpCookie idTokenCookie = cookieUtils.deleteCookie(IDTOKEN_COOKIE_KEY);

        // чтобы браузер применил куки к бразуеру - указываем их в заголовке Set-Cookie в response
        HttpHeaders responseHeaders = new HttpHeaders();
        responseHeaders.add(HttpHeaders.SET_COOKIE, accessTokenCookie.toString());
        responseHeaders.add(HttpHeaders.SET_COOKIE, refreshTokenCookie.toString());
        responseHeaders.add(HttpHeaders.SET_COOKIE, idTokenCookie.toString());
        return responseHeaders;
    }


}

