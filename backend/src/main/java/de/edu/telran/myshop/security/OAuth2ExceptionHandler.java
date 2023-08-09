package de.edu.telran.myshop.security;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * Handles OAuth2 errors during authentication and authorization.
 * <p>
 * This class implements the AuthenticationEntryPoint interface and provides
 * a custom way to handle authentication and authorization exceptions.
 */
public class OAuth2ExceptionHandler implements AuthenticationEntryPoint {

    /**
     * Commences the handling of OAuth2 authentication and authorization exceptions.
     *
     * @param request   The HTTP request that resulted in the exception.
     * @param response  The HTTP response that will be populated with the error details.
     * @param exception The authentication exception that occurred.
     * @throws IOException      If an I/O error occurs during writing the response.
     * @throws ServletException If a servlet error occurs during the handling process.
     */
    @Override
    public void commence(HttpServletRequest request, HttpServletResponse response, AuthenticationException exception) throws IOException, ServletException {
        final Map<String, Object> jsonBody = new HashMap<>();

        jsonBody.put("type", exception.getClass().getSimpleName());
        jsonBody.put("class", exception.getClass());
        jsonBody.put("message", exception.getMessage());
        jsonBody.put("exception", exception.getCause());
        jsonBody.put("path", request.getServletPath());
        jsonBody.put("timestamp", (new Date()).getTime());

        response.setContentType("application/json");
        response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);

        final ObjectMapper mapper = new ObjectMapper();
        mapper.writeValue(response.getOutputStream(), jsonBody);
    }
}
