package com.ignishers.backend.service;

import com.ignishers.backend.exception.TokenRefreshException;
import com.ignishers.backend.model.user.RefreshToken;
import com.ignishers.backend.model.user.User;
import com.ignishers.backend.repository.RefreshTokenRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.util.ReflectionTestUtils;

import java.time.Instant;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class RefreshTokenServiceTest {

    @Mock
    private RefreshTokenRepository repository;

    @InjectMocks
    private RefreshTokenService refreshTokenService;

    @BeforeEach
    void setUp() {
        // @Value field Mockito se inject nahi hota — manually set karna padta hai
        ReflectionTestUtils.setField(refreshTokenService, "refreshTokenExpiryMs", 604800000L);
    }

    @Test
    void createRefreshToken_shouldDeleteExisting_thenCreateNew() {
        User user = User.builder().id(1L).email("test@example.com").build();
        RefreshToken existingToken = RefreshToken.builder().id(10L).user(user).build();

        when(repository.findByUser(user)).thenReturn(Optional.of(existingToken));
        when(repository.save(any(RefreshToken.class))).thenAnswer(invocation -> invocation.getArgument(0));

        RefreshToken result = refreshTokenService.createRefreshToken(user);

        verify(repository, times(1)).delete(existingToken);
        verify(repository, times(1)).save(any(RefreshToken.class));
        assertNotNull(result.getToken());
        assertEquals(user, result.getUser());
    }

    @Test
    void verifyAndGet_shouldThrowException_whenTokenNotFound() {
        when(repository.findByToken("invalid-token")).thenReturn(Optional.empty());

        assertThrows(TokenRefreshException.class, () ->
                refreshTokenService.verifyAndGet("invalid-token"));
    }

    @Test
    void verifyAndGet_shouldThrowException_andDeleteToken_whenExpired() {
        RefreshToken expiredToken = RefreshToken.builder()
                .token("expired-token")
                .expiryDate(Instant.now().minusSeconds(10))   // already expired
                .build();

        when(repository.findByToken("expired-token")).thenReturn(Optional.of(expiredToken));

        assertThrows(TokenRefreshException.class, () ->
                refreshTokenService.verifyAndGet("expired-token"));

        verify(repository, times(1)).delete(expiredToken);
    }

    @Test
    void verifyAndGet_shouldReturnToken_whenValid() {
        RefreshToken validToken = RefreshToken.builder()
                .token("valid-token")
                .expiryDate(Instant.now().plusSeconds(3600))   // not expired
                .build();

        when(repository.findByToken("valid-token")).thenReturn(Optional.of(validToken));

        RefreshToken result = refreshTokenService.verifyAndGet("valid-token");

        assertEquals(validToken, result);
        verify(repository, never()).delete(any());
    }

    @Test
    void deleteByUser_shouldCallRepositoryDeleteByUser() {
        User user = User.builder().id(1L).build();

        refreshTokenService.deleteByUser(user);

        verify(repository, times(1)).deleteByUser(user);
    }
}
