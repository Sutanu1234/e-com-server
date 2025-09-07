package com.ecom.server.service;

import com.ecom.server.dto.*;

public interface AuthService {
    AuthResponse signup(SignupRequest signupRequest);
    AuthResponse login(LoginRequest loginRequest);
    UserResponse getCurrentUser(String email);
    UserResponse updateProfile(String currentEmail, UpdateProfileRequest request);
}