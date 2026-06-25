package com.ignishers.backend.service;

import com.ignishers.backend.dto.request.LoginRequest;
import com.ignishers.backend.dto.response.AuthResponse;

public interface AuthService {
    AuthResponse login(LoginRequest request);
    AuthResponse refreshToken(String refreshToken);
    void logout(String email);
}
