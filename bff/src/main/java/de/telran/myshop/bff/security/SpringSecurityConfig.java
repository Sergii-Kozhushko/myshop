package de.telran.myshop.bff.security;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.boot.autoconfigure.jdbc.DataSourceTransactionManagerAutoConfiguration;
import org.springframework.boot.autoconfigure.orm.jpa.HibernateJpaAutoConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import java.util.Arrays;

/**
 * Configuration class for setting up Spring Security in the BFF application.
 */
@Configuration
//@EnableWebSecurity
@EnableWebSecurity(debug = true)
@EnableAutoConfiguration(exclude = {DataSourceAutoConfiguration.class, DataSourceTransactionManagerAutoConfiguration.class, HibernateJpaAutoConfiguration.class})
public class SpringSecurityConfig {

    @Value("${client.url}")
    private String clientURL; // URL of the client application

    /**
     * Configure the SecurityFilterChain to handle security of requests.
     */
    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        //http.cors(); // Enable CORS settings

        http.authorizeRequests()
                .antMatchers("/bff/**").permitAll() // allow requests from bff
                .anyRequest().authenticated() // Allow access to all authorized requests
                .and()
                .csrf().disable() // Disable CSRF protection
                // Allow execution of OPTIONS requests without authorization
                .cors();


        // http.requiresChannel().anyRequest().requiresSecure(); // Require HTTPS for all requests

        http.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS); // Disable session creation

        return http.build();
    }

    /**
     * Configuration source for CORS requests made from the client application (bff).
     */
    @Bean
    CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration configuration = new CorsConfiguration();
        configuration.setAllowCredentials(true); // Allow sending cookies with CORS requests
        configuration.setAllowedOrigins(Arrays.asList(clientURL)); // Define which host to accept CORS from
        configuration.setAllowedHeaders(Arrays.asList("*"));
        configuration.setAllowedMethods(Arrays.asList("*"));
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", configuration);
        return source;
    }
}
