package ru.javabegin.oauth2.backend.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.http.HttpMethod;

// универсальный объект-контейнер, которое BFF получает из клиентского приложения (angular, react)
// в полях указаны данные - какой адрес нужно вызвать, каким методом, какой body добавить
// т.е. BFF просто использует эти данные для вызова конкретного Resource Server (из поля url) и также добавляет access token в запрос

@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
public class Operation {

    private HttpMethod httpMethod;
    private String url;
    private Object body;

}
