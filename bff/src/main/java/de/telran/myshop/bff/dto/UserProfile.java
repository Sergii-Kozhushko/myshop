package de.telran.myshop.bff.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * Represents the full user profile data for display in the frontend.
 */
@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
public class UserProfile {

    //private String givenName;
    //private String userName;
    //private String address;
    //private String phone;

    private String name; // The name of the user.
    private String email; // The email address of the user.
    private String id; // The unique identifier of the user.

    // Additional fields can be added as needed (from Keycloak or another Auth Server).
}
