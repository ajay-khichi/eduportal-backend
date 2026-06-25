package com.ignishers.backend.dto.response;

import lombok.Builder;
import java.util.List;

@Builder
public record UserResponse(
        Long id,
        String email,
        String name,
        String accountStatus,
        List<String> roles
) {}
