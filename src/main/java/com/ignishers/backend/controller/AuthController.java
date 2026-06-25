package com.ignishers.backend.controller;

import com.ignishers.backend.dto.request.FacultyRegisterRequest;
import com.ignishers.backend.dto.request.LoginRequest;
import com.ignishers.backend.dto.request.RefreshTokenRequest;
import com.ignishers.backend.dto.request.StudentRegisterRequest;
import com.ignishers.backend.dto.response.AuthResponse;
import com.ignishers.backend.service.AuthService;
import com.ignishers.backend.service.FacultyRegistrationService;
import com.ignishers.backend.service.StudentRegistrationService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/v1/auth")
@RequiredArgsConstructor
public class AuthController {
    private final AuthService authService;
    private final StudentRegistrationService studentRegistrationService;
    private final FacultyRegistrationService facultyRegistrationService;

    @PostMapping("/student/register")
    public ResponseEntity<AuthResponse> registerStudent(@Valid @RequestBody StudentRegisterRequest request) {
        return ResponseEntity.status(HttpStatus.CREATED).body(studentRegistrationService.registerStudent(request));
    }

    @PostMapping("/faculty/register")
    public ResponseEntity<AuthResponse> registerFaculty(@Valid @RequestBody FacultyRegisterRequest request) {
        return ResponseEntity.status(HttpStatus.CREATED).body(facultyRegistrationService.registerFaculty(request));
    }

    @PostMapping("/login")
    public ResponseEntity<AuthResponse> login(@Valid @RequestBody LoginRequest request) {
        return ResponseEntity.ok(authService.login(request));
    }

    @PostMapping("/refresh")
    public ResponseEntity<AuthResponse> refresh(@Valid @RequestBody RefreshTokenRequest request) {
        return ResponseEntity.ok(authService.refreshToken(request.refreshToken()));
    }

    @PostMapping("/logout")
    public ResponseEntity<Void> logout(Authentication authentication) {
        authService.logout(authentication.getName());
        return ResponseEntity.noContent().build();
    }
}
