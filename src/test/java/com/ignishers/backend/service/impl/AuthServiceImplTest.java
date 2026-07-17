package com.ignishers.backend.service.impl;

import com.ignishers.backend.dto.request.LoginRequest;
import com.ignishers.backend.dto.response.AuthResponse;
import com.ignishers.backend.dto.response.UserResponse;
import com.ignishers.backend.model.user.RefreshToken;
import com.ignishers.backend.model.user.User;
import com.ignishers.backend.repository.user.UserRepository;
import com.ignishers.backend.security.UserPrincipal;
import com.ignishers.backend.service.RefreshTokenService;
import com.ignishers.backend.util.JwtService;
import com.ignishers.backend.util.UserMapper;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class AuthServiceImplTest {

    @Mock
    private UserRepository userRepository;

    @Mock
    private AuthenticationManager authenticationManager;

    @Mock
    private JwtService jwtService;

    @Mock
    private RefreshTokenService refreshTokenService;

    @Mock
    private UserMapper userMapper;

    @Mock
    private Authentication authentication;

    @InjectMocks
    private AuthServiceImpl authService;

    @Test
    void login_ShouldReturnAuthResponse() {

        LoginRequest request = new LoginRequest(
                "ajay@gmail.com",
                "password"
        );

        User user = User.builder()
                .email("ajay@gmail.com")
                .password("encoded")
                .build();

        UserPrincipal principal = UserPrincipal.from(user);

        RefreshToken refreshToken = RefreshToken.builder()
                .token("refresh-token")
                .user(user)
                .build();

        UserResponse userResponse = mock(UserResponse.class);

        when(authenticationManager.authenticate(any(UsernamePasswordAuthenticationToken.class)))
                .thenReturn(authentication);

        when(authentication.getPrincipal())
                .thenReturn(principal);

        when(userRepository.findByEmail("ajay@gmail.com"))
                .thenReturn(Optional.of(user));

        when(jwtService.generateAccessToken(principal))
                .thenReturn("access-token");

        when(refreshTokenService.createRefreshToken(user))
                .thenReturn(refreshToken);

        when(userMapper.toUserResponse(user))
                .thenReturn(userResponse);

        AuthResponse response = authService.login(request);

        assertNotNull(response);
        assertEquals("access-token", response.token());
        assertEquals("refresh-token", response.refreshToken());
        assertEquals(userResponse, response.user());

        verify(authenticationManager).authenticate(any(UsernamePasswordAuthenticationToken.class));
        verify(userRepository).findByEmail("ajay@gmail.com");
    }

    @Test
    void login_ShouldThrowException_WhenUserNotFound() {

        LoginRequest request =
                new LoginRequest("ajay@gmail.com", "password");

        User user = User.builder()
                .email("ajay@gmail.com")
                .password("encoded")
                .build();

        UserPrincipal principal = UserPrincipal.from(user);

        when(authenticationManager.authenticate(any()))
                .thenReturn(authentication);

        when(authentication.getPrincipal())
                .thenReturn(principal);

        when(userRepository.findByEmail(anyString()))
                .thenReturn(Optional.empty());

        assertThrows(
                IllegalStateException.class,
                () -> authService.login(request)
        );
    }

    @Test
    void refreshToken_ShouldReturnNewTokens() {

        User user = User.builder()
                .email("ajay@gmail.com")
                .password("encoded")
                .build();

        RefreshToken storedToken = RefreshToken.builder()
                .token("old-refresh")
                .user(user)
                .build();

        RefreshToken newRefreshToken = RefreshToken.builder()
                .token("new-refresh")
                .user(user)
                .build();

        UserResponse userResponse = mock(UserResponse.class);

        when(refreshTokenService.verifyAndGet("old-refresh"))
                .thenReturn(storedToken);

        when(jwtService.generateAccessToken(any(UserPrincipal.class)))
                .thenReturn("new-access");

        when(refreshTokenService.createRefreshToken(user))
                .thenReturn(newRefreshToken);

        when(userMapper.toUserResponse(user))
                .thenReturn(userResponse);

        AuthResponse response =
                authService.refreshToken("old-refresh");

        assertEquals("new-access", response.token());
        assertEquals("new-refresh", response.refreshToken());
    }

    @Test
    void logout_ShouldDeleteRefreshTokens() {

        User user = User.builder()
                .email("ajay@gmail.com")
                .build();

        when(userRepository.findByEmail("ajay@gmail.com"))
                .thenReturn(Optional.of(user));

        authService.logout("ajay@gmail.com");

        verify(refreshTokenService).deleteByUser(user);
    }

    @Test
    void createAuthResponse_ShouldGenerateTokens() {

        User user = User.builder()
                .email("ajay@gmail.com")
                .password("encoded")
                .build();

        RefreshToken refreshToken = RefreshToken.builder()
                .token("refresh")
                .user(user)
                .build();

        UserResponse userResponse = mock(UserResponse.class);

        when(jwtService.generateAccessToken(any(UserPrincipal.class)))
                .thenReturn("access");

        when(refreshTokenService.createRefreshToken(user))
                .thenReturn(refreshToken);

        when(userMapper.toUserResponse(user))
                .thenReturn(userResponse);

        AuthResponse response =
                authService.createAuthResponse(user);

        assertEquals("access", response.token());
        assertEquals("refresh", response.refreshToken());
    }
}