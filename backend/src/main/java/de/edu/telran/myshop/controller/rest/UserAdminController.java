/**
 * UserController.java
 *
 * @author Sergii Kozhushko, sergiikozhushko@gmail.com
 * Date of creation: 15-Jul-2023 07:50
 */

package de.edu.telran.myshop.controller.rest;

import de.edu.telran.myshop.dto.UserDTO;
import de.edu.telran.myshop.entity.User;
import de.edu.telran.myshop.exception.ErrorMassage;
import de.edu.telran.myshop.exception.ProductNotFoundException;
import de.edu.telran.myshop.exception.UserAlreadyExistsException;
import de.edu.telran.myshop.keycloak.KeycloakUtils;
import lombok.RequiredArgsConstructor;
import org.keycloak.admin.client.CreatedResponseUtil;
import org.keycloak.representations.idm.UserRepresentation;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.ws.rs.core.Response;
import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping(UserAdminController.MAIN_PATH)
@RequiredArgsConstructor
public class UserAdminController {
    public static final String MAIN_PATH = "/admin/user";
    private final KeycloakUtils keycloakUtils;

    @GetMapping("/all")
    public ResponseEntity<List<UserRepresentation>> getAll() {
        return ResponseEntity.ok(keycloakUtils.getAll());
    }

    @PostMapping("/search")
    public ResponseEntity<List<UserRepresentation>> search(@RequestBody String searchText) {
        return ResponseEntity.ok(keycloakUtils.searchUserByText(searchText));
    }

    @PostMapping("/add")
    public ResponseEntity add(@RequestBody User user) {
        Response createdResponse = keycloakUtils.createKeycloakUser(user);
        if (createdResponse.getStatus() == 409) {
            throw new UserAlreadyExistsException(ErrorMassage.USER_EMAIL_NAME_DUPLICATE);

        }
        String userId = CreatedResponseUtil.getCreatedId(createdResponse);
        System.out.printf("User created with userID %s", userId);

        keycloakUtils.addRoles(userId, user.getRoles());

        return ResponseEntity.status(createdResponse.getStatus()).build();
    }

    @PostMapping("/deletebyid")
    public ResponseEntity deleteByUserId(@RequestBody String userId) {

        keycloakUtils.deleteUser(userId.trim());
        return new ResponseEntity(HttpStatus.OK);

    }


}
