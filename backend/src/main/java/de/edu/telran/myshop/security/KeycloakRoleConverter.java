package de.edu.telran.myshop.security;

import org.springframework.core.convert.converter.Converter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.oauth2.jwt.Jwt;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * A converter class that transforms JWT data into Spring Security roles.
 * <p>
 * This class implements the Converter interface to convert JWT claims into a collection
 * of GrantedAuthority objects representing roles in Spring Security.
 * <p>
 * The JWT is expected to contain a "realm_access" JSON section that lists the roles.
 * The roles are transformed into Spring Security roles by adding the "ROLE_" prefix.
 */
public class KeycloakRoleConverter implements Converter<Jwt, Collection<GrantedAuthority>> {

    /**
     * Converts JWT claims into a collection of GrantedAuthority objects.
     *
     * @param jwt The JWT containing the claims, including realm access roles.
     * @return A collection of GrantedAuthority objects representing the roles.
     */
    @Override
    public Collection<GrantedAuthority> convert(Jwt jwt) {

        // Access the "realm_access" JSON section
        Map<String, Object> realmAccess = (Map<String, Object>) jwt.getClaims().get("realm_access");

        // If the "realm_access" JSON section is not found, there are no roles
        if (realmAccess == null || realmAccess.isEmpty()) {
            return new ArrayList<>(); // Empty collection - no roles
        }

        // Convert roles from JWT to Spring Security roles using functional code
        Collection<GrantedAuthority> returnValue = ((List<String>) realmAccess.get("roles"))
                .stream().map(roleName -> "ROLE_" + roleName)
                .map(SimpleGrantedAuthority::new)
                .collect(Collectors.toList());
        return returnValue;
    }
}
