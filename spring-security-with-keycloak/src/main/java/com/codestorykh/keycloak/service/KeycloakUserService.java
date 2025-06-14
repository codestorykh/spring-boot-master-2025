package com.codestorykh.keycloak.service;

import com.codestorykh.keycloak.config.KeycloakProperties;
import com.codestorykh.keycloak.dto.UserRegistrationDto;
import com.codestorykh.keycloak.exception.KeycloakException;
import jakarta.ws.rs.core.Response;
import org.keycloak.admin.client.Keycloak;
import org.keycloak.admin.client.resource.RolesResource;
import org.keycloak.admin.client.resource.UsersResource;
import org.keycloak.representations.idm.CredentialRepresentation;
import org.keycloak.representations.idm.RoleRepresentation;
import org.keycloak.representations.idm.UserRepresentation;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;

@Service
public class KeycloakUserService implements KeycloakService {

    private final Keycloak keycloak;
    private final KeycloakProperties keycloakProperties;

    public KeycloakUserService(Keycloak keycloak, KeycloakProperties keycloakProperties) {
        this.keycloak = keycloak;
        this.keycloakProperties = keycloakProperties;
    }

    @Override
    public Response registerUser(UserRegistrationDto userDto) {
        try {
            UsersResource usersResource = keycloak.realm(keycloakProperties.getRealm()).users();
            RolesResource rolesResource = keycloak.realm(keycloakProperties.getRealm()).roles();

            // Create user representation
            UserRepresentation user = createUserRepresentation(userDto);

            // Create user
            Response response = usersResource.create(user);

            if (response.getStatus() == 201) {
                String userId = getUserIdFromResponse(response);
                ensureUserRoleExists(rolesResource);
                assignRole(userId, "user");
                return response;
            } else {
                String errorMessage = response.readEntity(String.class);
                throw new KeycloakException("Failed to create user: " + errorMessage);
            }
        } catch (Exception e) {
            throw new KeycloakException("Error creating user: " + e.getMessage(), e);
        }
    }

    @Override
    public void deleteUser(String userId) {
        try {
            keycloak.realm(keycloakProperties.getRealm()).users().get(userId).remove();
        } catch (Exception e) {
            throw new KeycloakException("Error deleting user: " + e.getMessage(), e);
        }
    }

    @Override
    public void updateUser(String userId, UserRegistrationDto userDto) {
        try {
            UsersResource usersResource = keycloak.realm(keycloakProperties.getRealm()).users();
            UserRepresentation user = createUserRepresentation(userDto);
            usersResource.get(userId).update(user);
        } catch (Exception e) {
            throw new KeycloakException("Error updating user: " + e.getMessage(), e);
        }
    }

    @Override
    public void assignRole(String userId, String roleName) {
        try {
            UsersResource usersResource = keycloak.realm(keycloakProperties.getRealm()).users();
            RolesResource rolesResource = keycloak.realm(keycloakProperties.getRealm()).roles();
            
            RoleRepresentation role = rolesResource.get(roleName).toRepresentation();
            usersResource.get(userId).roles().realmLevel().add(Collections.singletonList(role));
        } catch (Exception e) {
            throw new KeycloakException("Error assigning role: " + e.getMessage(), e);
        }
    }

    private UserRepresentation createUserRepresentation(UserRegistrationDto userDto) {
        UserRepresentation user = new UserRepresentation();
        user.setEnabled(true);
        user.setUsername(userDto.username());
        user.setEmail(userDto.email());
        user.setFirstName(userDto.firstName());
        user.setLastName(userDto.lastName());
        user.setEmailVerified(false);

        CredentialRepresentation credential = new CredentialRepresentation();
        credential.setType(CredentialRepresentation.PASSWORD);
        credential.setValue(userDto.password());
        credential.setTemporary(false);
        user.setCredentials(Collections.singletonList(credential));

        return user;
    }

    private String getUserIdFromResponse(Response response) {
        return response.getLocation().getPath().substring(response.getLocation().getPath().lastIndexOf('/') + 1);
    }

    private void ensureUserRoleExists(RolesResource rolesResource) {
        try {
            rolesResource.get("user").toRepresentation();
        } catch (Exception e) {
            RoleRepresentation userRole = new RoleRepresentation();
            userRole.setName("user");
            userRole.setDescription("Regular user role");
            rolesResource.create(userRole);
        }
    }
} 