package com.ignishers.backend.service;

import com.ignishers.backend.dto.request.FacultyRegisterRequest;
import com.ignishers.backend.dto.response.AuthResponse;

public interface FacultyRegistrationService {
    AuthResponse registerFaculty(FacultyRegisterRequest request);
}
