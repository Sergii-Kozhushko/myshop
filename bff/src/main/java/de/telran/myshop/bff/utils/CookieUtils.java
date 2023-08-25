package de.telran.myshop.bff.utils;

import org.apache.tomcat.util.http.SameSiteCookies;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpCookie;
import org.springframework.http.ResponseCookie;
import org.springframework.stereotype.Component;

/**
 * Utility for working with cookies.
 *
 * Note: JWT cookies are created on the server and managed only by the server (created, deleted) - "server-side cookie".
 * On the client side, these cookies cannot be accessed using JavaScript (due to the httpOnly flag) for security and protection against XSS attacks.
 * Also, the channel must be HTTPS to prevent decryption of request data between the client (browser) and the server.
 */
@Component
public class CookieUtils {

    @Value("${cookie.domain}")
    private String cookieDomain; // The domain that will be set in the server-side cookie upon its creation on the backend

    /**
     * Creates a server-side cookie with the JWT value. Note: Only the server can read this cookie, the client cannot using JS or other client code (for security).
     * @param name Name of the cookie
     * @param value Value of the JWT
     * @param durationInSeconds Duration of the cookie's validity in seconds
     * @return The created HttpCookie
     */
    public HttpCookie createCookie(String name, String value, int durationInSeconds) {
        return ResponseCookie
                .from(name, value)
                .maxAge(durationInSeconds) // 86400 seconds = 1 day
                .sameSite(SameSiteCookies.STRICT.getValue())
                .httpOnly(true)
                .secure(true)
                .domain(cookieDomain)
                .path("/")
                .build();
    }

    /**
     * Deletes (nullifies) a cookie.
     * @param name Name of the cookie to delete
     * @return The deleted HttpCookie
     */
    public HttpCookie deleteCookie(String name) {
        return ResponseCookie
                .from(name, "")
                .maxAge(0)
                .sameSite(SameSiteCookies.STRICT.getValue())
                .httpOnly(true)
                .secure(true)
                .domain(cookieDomain)
                .path("/")
                .build();
    }
}
