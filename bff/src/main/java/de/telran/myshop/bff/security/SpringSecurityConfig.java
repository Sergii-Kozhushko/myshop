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

@Configuration // данный класс будет считан как конфиг для spring контейнера
@EnableWebSecurity(debug = true) // включает механизм защиты адресов, которые настраиваются в SecurityFilterChain
//
//// для BFF не нунжно исп-е БД в нашем варианте, поэтому отключаем автоконфигурацию связи с БД
@EnableAutoConfiguration(exclude = {DataSourceAutoConfiguration.class, DataSourceTransactionManagerAutoConfiguration.class, HibernateJpaAutoConfiguration.class})

public class SpringSecurityConfig {

    @Value("${client.url}")
    private String clientURL; // клиентский URL


    // создается спец. бин, который отвечает за настройки запросов по http (метод вызывается автоматически) Spring контейнером
    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {

        // все сетевые настройки
        http.cors();
        http.authorizeRequests()
                //.antMatchers("/bff/**").permitAll() // разрешаем запросы на bff
                .anyRequest().permitAll()
                //.anyRequest().authenticated() // остальной API будет доступен только аутентифицированным пользователям
                .and()
                .csrf().disable() // отключаем встроенную защиту от CSRF атак, т.к. используем свою, из OAUTH2
        //        .cors()
        ;// разрешает выполнять OPTIONS запросы от клиента (preflight запросы) без авторизации

        http.requiresChannel().anyRequest().requiresSecure(); // обязательное исп. HTTPS для всех запросах

        // отключаем создание куков для сессии
        http.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);

        return http.build();
    }


    // Cors-request are requests, when makes request to another host (bff)
    @Bean
    CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration configuration = new CorsConfiguration();
        configuration.setAllowCredentials(true); // allow to send cookies with cors-requests
        configuration.setAllowedOrigins(Arrays.asList(clientURL));// from which host to accept cors
        configuration.setAllowedHeaders(Arrays.asList("*"));
        configuration.setAllowedMethods(Arrays.asList("*"));
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", configuration);
        return source;
    }
}
