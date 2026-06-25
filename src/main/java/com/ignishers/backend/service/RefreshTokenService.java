package com.ignishers.backend.service;

import com.ignishers.backend.exception.TokenRefreshException;
import com.ignishers.backend.model.user.RefreshToken;
import com.ignishers.backend.model.user.User;
import com.ignishers.backend.repository.RefreshTokenRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Instant;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class RefreshTokenService {

    private final RefreshTokenRepository repository;

    @Value("${application.security.jwt.refresh-token.expiration}")
    private long refreshTokenExpiryMs;

//    create or update refresh token for the given user
    @Transactional
    public RefreshToken createRefreshToken(User user) {
        repository.findByUser(user).ifPresent(repository::delete);

        RefreshToken refreshToken = RefreshToken.builder()
                .user(user)
                .token(UUID.randomUUID().toString())
                .expiryDate(Instant.now().plusMillis(refreshTokenExpiryMs))
                .build();

        return  repository.save(refreshToken);
    }

    @Transactional(readOnly = true)
    public RefreshToken verifyAndGet(String token) {
        RefreshToken refreshToken = repository.findByToken(token)
                .orElseThrow(()-> new TokenRefreshException("Refresh token not found"));

        if(refreshToken.isExpired()) {
            repository.delete(refreshToken);
            throw new TokenRefreshException("Refresh token expired. Please log in again.");
        }
        return refreshToken;
    }

    @Transactional
    public void deleteByUser(User user) {
        repository.deleteByUser(user);
    }
}
