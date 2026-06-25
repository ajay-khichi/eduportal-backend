package com.ignishers.backend.service.impl;

import com.ignishers.backend.dto.request.LoginRequest;
import com.ignishers.backend.dto.response.AuthResponse;
import com.ignishers.backend.model.user.*;
import com.ignishers.backend.repository.user.UserRepository;
import com.ignishers.backend.security.UserPricipal;
import com.ignishers.backend.service.AuthService;
import com.ignishers.backend.service.RefreshTokenService;
import com.ignishers.backend.util.JwtService;
import com.ignishers.backend.util.UserMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService {

    private final UserRepository userRepository;
    private final AuthenticationManager authenticationManager;
    private final JwtService jwtService;
    private final RefreshTokenService refreshTokenService;
    private final UserMapper userMapper;

    @Override
    public AuthResponse login(LoginRequest request) {
        Authentication authentication =  authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(request.email(), request.password())
        );

        UserDetails principal = (UserPricipal) authentication.getPrincipal();
        assert principal != null;
        User user = userRepository.findByEmail(principal.getUsername())
                .orElseThrow(() -> new IllegalStateException("Authenticated user vanished from DB"));
        return buildAuthResponse(user);
    }

    @Override
    @Transactional
    public AuthResponse refreshToken(String refreshToken) {
        RefreshToken storedToken =  refreshTokenService.verifyAndGet(refreshToken);
        User user =  storedToken.getUser();

        UserDetails principal =  UserPricipal.from(user);
        String newAccessToken = jwtService.generateAccessToken(principal);

        RefreshToken newRefreshToken = refreshTokenService.createRefreshToken(user);

        return AuthResponse.builder()
                .token(newAccessToken)
                .refreshToken(newRefreshToken.getToken())
                .user(userMapper.toUserResponse(user))
                .build();
    }

    @Override
    @Transactional
    public void logout(String email) {
        User user = userRepository.findByEmail(email)
                .orElseThrow(()-> new IllegalStateException("user not found: " + email));
        refreshTokenService.deleteByUser(user);
    }

    public AuthResponse buildAuthResponse(User user) {
        UserDetails userDetails  = UserPricipal.from(user);
        String accessToken =  jwtService.generateAccessToken(userDetails);
        RefreshToken refreshToken = refreshTokenService.createRefreshToken(user);

        return AuthResponse.builder()
                .token(accessToken)
                .refreshToken(refreshToken.getToken())
                .user(userMapper.toUserResponse(user))
                .build();
    }
}
