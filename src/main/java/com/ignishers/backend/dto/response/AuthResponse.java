package com.ignishers.backend.dto.response;

import lombok.Builder;

@Builder
public record AuthResponse(
        String token,
        String refreshToken,
        String type,
        UserResponse user
) {
    // Compact constructor to automatically set "Bearer" as the default token type
    public AuthResponse {
        if (type == null || type.isBlank()) {
            type = "Bearer";
        }
    }
}
