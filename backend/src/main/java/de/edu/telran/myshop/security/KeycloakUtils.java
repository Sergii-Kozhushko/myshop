package de.edu.telran.myshop.security;

import de.edu.telran.myshop.dto.UserDTO;
import de.edu.telran.myshop.entity.User;
import de.edu.telran.myshop.exception.ErrorMassage;
import de.edu.telran.myshop.exception.UserNotFoundException;
import org.keycloak.OAuth2Constants;
import org.keycloak.admin.client.Keycloak;
import org.keycloak.admin.client.KeycloakBuilder;
import org.keycloak.admin.client.resource.RealmResource;
import org.keycloak.admin.client.resource.UserResource;
import org.keycloak.admin.client.resource.UsersResource;
import org.keycloak.representations.idm.CredentialRepresentation;
import org.keycloak.representations.idm.RoleRepresentation;
import org.keycloak.representations.idm.UserRepresentation;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;


import javax.annotation.PostConstruct;
import javax.ws.rs.NotFoundException;
import javax.ws.rs.core.Response;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Utility class for interacting with Keycloak.
 * <p>
 * This class provides methods for creating, updating, deleting, and searching users
 * in a Keycloak realm. It also handles roles and user attributes.
 */
@Service
public class KeycloakUtils {

    @Value("${keycloak.auth-server-url}")
    private String serverURL;

    @Value("${keycloak.realm}")
    private String realm;

    @Value("${keycloak.resource}")
    private String clientId;

    @Value("${keycloak.credentials.secret}")
    private String clientSecret;

    private static Keycloak keycloak;
    private static RealmResource realmResource;
    private static UsersResource usersResource;

    /**
     * Initializes the Keycloak instance.
     * <p>
     * -@PostConstruct means that this method is called by Spring after bean initialization.
     *
     * @return The initialized Keycloak instance.
     */
    @PostConstruct
    public Keycloak initKeyCloak() {
        if (keycloak == null) {
            // get access to KC realm
            keycloak = KeycloakBuilder.builder()
                    .realm(realm)
                    .serverUrl(serverURL)
                    .clientId(clientId)
                    .clientSecret(clientSecret)
                    .grantType(OAuth2Constants.CLIENT_CREDENTIALS)
                    .build();

            realmResource = keycloak.realm(realm);
            usersResource = realmResource.users();
        }
        return keycloak;
    }

    /**
     * Creates a new user in Keycloak.
     *
     * @param user The user to be created.
     * @return The response from Keycloak indicating the success or failure of the user creation.
     */
    public Response createKeycloakUser(User user) {

        // Password data - a special container object CredentialRepresentation
        CredentialRepresentation credentialRepresentation = createPasswordCredentials(user.getPassword());

        // User data (you can add or remove fields based on the required functionality)
        // Special container object UserRepresentation
        UserRepresentation kcUser = new UserRepresentation();
        kcUser.setUsername(user.getUsername());
        kcUser.setCredentials(Collections.singletonList(credentialRepresentation));
        kcUser.setEmail(user.getEmail());
        kcUser.setEnabled(true);
        kcUser.setEmailVerified(false);

        // Invoking Keycloak API (the library handles internal operations - constructs REST requests, fills parameters
        // , etc.)
        Response response = usersResource.create(kcUser);

        return response;
    }


    /**
     * Creates a CredentialRepresentation object for a password.
     *
     * @param password The password for which the CredentialRepresentation is created.
     * @return A CredentialRepresentation object containing the password information.
     */
    private CredentialRepresentation createPasswordCredentials(String password) {
        CredentialRepresentation passwordCredentials = new CredentialRepresentation();
        passwordCredentials.setTemporary(false);
        passwordCredentials.setType(CredentialRepresentation.PASSWORD);
        passwordCredentials.setValue(password);
        return passwordCredentials;
    }

    public void addRoles(String userId, List<String> roles) {
        List<RoleRepresentation> kcRoles = new ArrayList<>();
        for (String role : roles) {
            RoleRepresentation rolerep = realmResource.roles().get(role).toRepresentation();
            kcRoles.add(rolerep);
        }

        UserResource uniqueUserResource = usersResource.get(userId);
        uniqueUserResource.roles().realmLevel().add(kcRoles);
    }

    /**
     * Deletes a user from the Keycloak realm.
     *
     * @param userId The ID of the user to be deleted.
     * @throws UserNotFoundException if the user with the provided ID is not found.
     */
    public void deleteUser(String userId) {
        UserResource uniqueUserResource;
        try {
            uniqueUserResource = usersResource.get(userId);
            uniqueUserResource.remove();
        } catch (NotFoundException ex) {
            throw new UserNotFoundException(ErrorMassage.USER_UUID_NOT_FOUND);
        }
    }

    /**
     * Retrieves a user's representation by their ID.
     *
     * @param userId The ID of the user.
     * @return The UserRepresentation object representing the user.
     */
    public UserRepresentation findUserById(String userId) {
        return usersResource.get(userId).toRepresentation();
    }

    /**
     * Searches for users in the Keycloak realm based on a text attribute.
     *
     * @param text The text attribute to search for.
     * @return A list of UserRepresentation objects matching the search criteria.
     */
    public List<UserRepresentation> searchUserByText(String text) {
        return usersResource.searchByAttributes(text);
    }

    /**
     * Retrieves a list of all users in the Keycloak realm.
     *
     * @return A list of UserRepresentation objects representing all users.
     */
    public List<UserRepresentation> getAll() {
        return usersResource.list();
    }

    /**
     * Updates a user's information in the Keycloak realm.
     *
     * @param user The UserDTO object containing the updated user information.
     */
    public void updateUser(UserDTO user) {
        CredentialRepresentation credentialRepresentation = createPasswordCredentials(user.getPassword());
        UserRepresentation kcUser = new UserRepresentation();
        kcUser.setUsername(user.getUsername());
        kcUser.setCredentials(Collections.singletonList(credentialRepresentation));
        kcUser.setEmail(user.getEmail());
        UserResource userResource = usersResource.get(user.getId());
        userResource.update(kcUser);
    }
}