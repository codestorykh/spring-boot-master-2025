package com.codestorykh.keycloak.service;

import com.codestorykh.keycloak.dto.UserRegistrationDto;
import jakarta.ws.rs.core.Response;

public interface KeycloakService {
    Response registerUser(UserRegistrationDto userDto);
    void deleteUser(String userId);
    void updateUser(String userId, UserRegistrationDto userDto);
    void assignRole(String userId, String roleName);
} 