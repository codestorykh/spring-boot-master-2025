package com.codestorykh.keycloak.controller;

import com.codestorykh.keycloak.dto.UserRegistrationDto;
import com.codestorykh.keycloak.service.KeycloakUserService;
import jakarta.validation.Valid;
import jakarta.ws.rs.core.Response;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/v1/public")
public class UserRegistrationController {

    private final KeycloakUserService userService;

    public UserRegistrationController(KeycloakUserService userService) {
        this.userService = userService;
    }

    @PostMapping("/register")
    public ResponseEntity<String> registerUser(@Valid @RequestBody UserRegistrationDto userDto) {
        try {
            Response response = userService.registerUser(userDto);
            
            if (response.getStatus() == 201) {
                return ResponseEntity.ok("User registered successfully");
            } else {
                String errorMessage = response.readEntity(String.class);
                return ResponseEntity.badRequest().body("Failed to register user: " + errorMessage);
            }
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Failed to register user: " + e.getMessage());
        }
    }
} 