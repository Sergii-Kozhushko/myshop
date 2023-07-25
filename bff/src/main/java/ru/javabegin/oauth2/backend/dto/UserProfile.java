package ru.javabegin.oauth2.backend.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter

// полные данные профиля пользователя, для отображения в frontend
public class UserProfile {

    //private String givenName;
//    private String userName;
//    private String address;
    //private String phone;
    private String name;
    private String email;
    private String id;

    // можно добавлять любые поля, которые вам необходимы (из keycloak или другого Auth Server)

}
