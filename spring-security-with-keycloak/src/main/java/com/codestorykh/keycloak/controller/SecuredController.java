package com.codestorykh.keycloak.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
@RequestMapping("/v1")
public class SecuredController {

    @GetMapping("/user/profile")
    @PreAuthorize("hasRole('USER')")
    public ResponseEntity<Map<String, Object>> userProfile(@AuthenticationPrincipal Jwt jwt) {
        Map<String, Object> profile = Map.of(
            "subject", jwt.getSubject(),
            "email", jwt.getClaimAsString("email"),
            "name", jwt.getClaimAsString("name"),
            "preferred_username", jwt.getClaimAsString("preferred_username")
        );
        return ResponseEntity.ok(profile);
    }

    @GetMapping("/authenticated")
    public ResponseEntity<String> authenticatedEndpoint() {
        return ResponseEntity.ok("This endpoint is accessible by any authenticated user");
    }
} 