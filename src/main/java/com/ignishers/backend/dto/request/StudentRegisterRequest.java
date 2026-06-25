package com.ignishers.backend.dto.request;

import jakarta.validation.constraints.*;
import lombok.Builder;

@Builder
public record StudentRegisterRequest(

        @NotBlank(message = "Email is required")
        @Email(message = "Email must be valid")
        String email,

        @NotBlank(message = "Password is required")
        @Size(min = 8, max = 100, message = "Password must be at least 8 characters")
        String password,

        @NotBlank(message = "Full name is required")
        @Size(min = 2, max = 100, message = "Full name must be between 2 and 100 characters")
        String name,

        @NotBlank(message = "Enrollment number is required")
        @Size(min = 6, max = 28, message = "Enrollment Number must be between 6 and 28 characters")
        String enrollmentNo,

        @NotNull(message = "Admission year is required")
        Integer admissionYear,

        @NotNull(message = "Current semester is required")
        @Min(value = 1, message = "Semester must be at least 1")
        Integer currentSemester,

        @NotNull(message = "Department ID is required")
        Long departmentId,

        @NotNull(message = "Program ID is required")
        Long programId,

        @NotNull(message = "Curriculum ID is required")
        Long curriculumId
) {}
