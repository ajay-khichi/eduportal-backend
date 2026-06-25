package com.ignishers.backend.service;

import com.ignishers.backend.dto.request.StudentRegisterRequest;
import com.ignishers.backend.dto.response.AuthResponse;

public interface StudentRegistrationService {
    AuthResponse registerStudent(StudentRegisterRequest request);
}
