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

// обрабатывает ошибки oauth2 (авторизация и пр.)
public class OAuth2ExceptionHandler implements AuthenticationEntryPoint {
    @Override
    public void commence(HttpServletRequest request, HttpServletResponse response, AuthenticationException exception) throws IOException, ServletException
    {
        final Map<String, Object> jsonBody = new HashMap<>() ;

        jsonBody.put("type"    , exception.getClass().getSimpleName()) ;
        jsonBody.put("class"    , exception.getClass()) ;
        jsonBody.put("message"  , exception.getMessage()) ;
        jsonBody.put("exception", exception.getCause()) ;
        jsonBody.put("path"     , request.getServletPath()) ;
        jsonBody.put("timestamp", (new Date()).getTime()) ;

        response.setContentType("application/json") ;
        response.setStatus(HttpServletResponse.SC_UNAUTHORIZED) ;

        final ObjectMapper mapper = new ObjectMapper() ;
        mapper.writeValue(response.getOutputStream(), jsonBody) ;
    }
}
