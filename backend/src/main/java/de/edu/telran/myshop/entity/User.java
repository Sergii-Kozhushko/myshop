package de.edu.telran.myshop.entity;

import lombok.*;

import javax.persistence.*;
import java.util.List;


/**
 * Represents a user entity in the application.
 * Stored in Keycloak
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class User {

    private String id;

    private String name;

    private String email;
    private String username;
    private String password;

    private List<String> roles;


}
