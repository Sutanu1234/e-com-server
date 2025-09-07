package com.ecom.server.controller;

import com.ecom.server.dto.*;
import com.ecom.server.service.AuthService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.*;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
@CrossOrigin(origins = "http://localhost:5173")
@RequiredArgsConstructor
public class AuthController {
    private final AuthService authService;

    @PostMapping("/signup")
    public ResponseEntity<AuthResponse> signup(@Validated @RequestBody SignupRequest req) {
        AuthResponse resp = authService.signup(req);
        return ResponseEntity.status(HttpStatus.CREATED).body(resp);
    }

    // single login URL for both admin and users
    @PostMapping("/login")
    public ResponseEntity<AuthResponse> login(@Validated @RequestBody LoginRequest req) {
        AuthResponse resp = authService.login(req);
        return ResponseEntity.ok(resp);
    }

    @GetMapping("/me")
    public ResponseEntity<UserResponse> getCurrentUser(@AuthenticationPrincipal String email) {
        return ResponseEntity.ok(authService.getCurrentUser(email));
    }

    @PutMapping("/update-profile")
    public ResponseEntity<UserResponse> updateProfile(
            @AuthenticationPrincipal String currentEmail,
            @RequestBody UpdateProfileRequest request
    ) {
        UserResponse updatedUser = authService.updateProfile(currentEmail, request);
        return ResponseEntity.ok(updatedUser);
    }
}