package de.edu.telran.myshop.security;

import de.edu.telran.myshop.config.UriLinks;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationConverter;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.firewall.StrictHttpFirewall;

/**
 * Configuration class for Spring Security settings.
 * <p>
 * This class defines security settings for protecting URLs and enabling global method security.
 */
@Configuration // This class will be recognized as a configuration for the Spring container
@EnableWebSecurity // Enables URL protection mechanism configured in SecurityFilterChain
@EnableGlobalMethodSecurity(prePostEnabled = true) // Enables method-level security based on roles
public class SpringSecurityConfig {

    @Value("${client.url}")
    private String clientURL; // Client URL

    /**
     * Creates a SecurityFilterChain bean to configure HTTP requests.
     *
     * @param http The HttpSecurity object to configure.
     * @return The configured SecurityFilterChain.
     * @throws Exception If an exception occurs during configuration.
     */
    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        // Converter for Spring Security configuration
        JwtAuthenticationConverter jwtAuthenticationConverter = new JwtAuthenticationConverter();
        // Attach role converter
        jwtAuthenticationConverter.setJwtGrantedAuthoritiesConverter(new KeycloakRoleConverter());

        // All network settings
        http
                .authorizeRequests()
                .antMatchers("/swagger-ui/**").permitAll()
                .antMatchers("/admin/*").hasRole("admin") // CRUD operations for user management
                .antMatchers("/user/*").hasRole("user") // User's own actions (registration, etc.)
                .antMatchers(UriLinks.CATEGORY_URI + "/*").hasRole("user")
                .antMatchers(UriLinks.PRODUCT_URI + "/*").hasRole("user")
                .antMatchers(UriLinks.SUPPLY_URI + "/*").hasRole("user")
                .antMatchers(UriLinks.SALE_URI + "/*").hasRole("user")
                .antMatchers(UriLinks.CUSTOMER_URI + "/*").hasRole("admin")
                .antMatchers(UriLinks.SUPPLIER_URI + "/*").hasRole("admin")
                // Additional antMatchers can be configured here
                .and()
                .csrf().disable() // Disable built-in CSRF protection, using OAuth2's
                .cors() // Allow OPTIONS requests (preflight) without authorization
                .and()
                .oauth2ResourceServer() // Enable OAuth2 protection for this backend
                .jwt()
                .jwtAuthenticationConverter(jwtAuthenticationConverter)
                .and()
                .authenticationEntryPoint(new OAuth2ExceptionHandler())
        ; // Attach role converter from JWT to Authority (Role)

        return http.build();
    }

    /**
     * Creates a StrictHttpFirewall bean to prevent requests with invalid header values.
     *
     * @return The configured StrictHttpFirewall.
     */
    @Bean
    public StrictHttpFirewall httpFirewall() {
        StrictHttpFirewall firewall = new StrictHttpFirewall();
        firewall.setAllowedHeaderNames((header) -> true);
        firewall.setAllowedHeaderValues((header) -> true); // Do not reject requests with non-ISO characters
        firewall.setAllowedParameterNames((parameter) -> true);
        return firewall;
    }
}