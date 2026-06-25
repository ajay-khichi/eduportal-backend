package com.ignishers.backend.dto.response;

import com.ignishers.backend.model.user.UserRole;
import lombok.Builder;
import java.util.List;

@Builder
public record UserResponse(
        Long id,
        String email,
        String accountStatus,
        List<UserRole> roles
) {}
