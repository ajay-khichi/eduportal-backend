package com.ignishers.backend.dto.request;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Builder;

@Builder
public record FacultyRegisterRequest(
        @NotBlank(message = "Email is required")
        @Email(message = "Invalid email format")
        String email,

        @NotBlank(message = "Password is required")
        @Size(min = 8, max = 100, message = "Password must be at least 8 characters")
        String password,

        @NotBlank(message = "Faculty name is required")
        @Size(min = 2, max = 100, message = "Full name must be between 2 and 100 characters")
        String name,

        @NotBlank(message = "Employee code is required")
        String employeeCode,

        String designation,

        @NotNull(message = "Department ID is required")
        Long departmentId
) {}
