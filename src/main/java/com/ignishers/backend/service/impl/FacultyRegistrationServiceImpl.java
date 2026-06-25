package com.ignishers.backend.service.impl;

import com.ignishers.backend.dto.request.FacultyRegisterRequest;
import com.ignishers.backend.dto.response.AuthResponse;
import com.ignishers.backend.exception.EmailAlreadyExistsException;
import com.ignishers.backend.model.enums.AccountStatus;
import com.ignishers.backend.model.enums.FacultyStatus;
import com.ignishers.backend.model.enums.RoleName;
import com.ignishers.backend.model.organization.Department;
import com.ignishers.backend.model.user.Faculty;
import com.ignishers.backend.model.user.Role;
import com.ignishers.backend.model.user.User;
import com.ignishers.backend.model.user.UserRole;
import com.ignishers.backend.repository.organization.DepartmentRepository;
import com.ignishers.backend.repository.user.FacultyRepository;
import com.ignishers.backend.repository.user.RoleRepository;
import com.ignishers.backend.repository.user.UserRepository;
import com.ignishers.backend.service.FacultyRegistrationService;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class FacultyRegistrationServiceImpl implements FacultyRegistrationService {

    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final FacultyRepository facultyRepository;
    private final DepartmentRepository departmentRepository;
    private final PasswordEncoder passwordEncoder;
    private final AuthServiceImpl authService;

    @Override
    @Transactional
    public AuthResponse registerFaculty(FacultyRegisterRequest request) {
        validateEmailNotTaken(request.email());

        User user = createUser(request);
        assignStudentRole(user);

        Faculty faculty = createFacultyProfile(user, request);

        userRepository.save(user);
        facultyRepository.save(faculty);

        return authService.buildAuthResponse(user);
    }

    private void validateEmailNotTaken(@NotBlank(message = "Email is required") @Email(message = "Email must be valid") String email) {
        if (userRepository.existsByEmail(email)) {
            throw new EmailAlreadyExistsException(email);
        }
    }

    private User createUser(FacultyRegisterRequest request) {
        return User.builder()
                .email(request.email())
                .password(passwordEncoder.encode(request.password()))
                .accountStatus(AccountStatus.ACTIVE)
                .build();
    }

    private void assignStudentRole(User user) {
        Role studentRole =  roleRepository.findByRoleName(RoleName.FACULTY)
                .orElseThrow(() -> new IllegalStateException("FACULTY role not seeded"));

        UserRole userRole = UserRole.builder()
                .user(user)
                .role(studentRole)
                .build();

        user.addRole(userRole);

    }

    private Faculty createFacultyProfile(User user, FacultyRegisterRequest request) {
        Department department = departmentRepository.findById(request.departmentId())
                .orElseThrow(() -> new IllegalStateException("Department Not Found!"));

        return Faculty.builder()
                .user(user)
                .department(department)
                .name(request.name())
                .employeeCode(request.employeeCode())
                .designation(request.designation())
                .facultyStatus(FacultyStatus.ACTIVE)
                .build();
    }
}
