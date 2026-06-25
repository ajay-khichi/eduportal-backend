package com.ignishers.backend.service.impl;

import com.ignishers.backend.dto.request.StudentRegisterRequest;
import com.ignishers.backend.dto.response.AuthResponse;
import com.ignishers.backend.exception.EmailAlreadyExistsException;
import com.ignishers.backend.model.academic.Curriculum;
import com.ignishers.backend.model.enums.AccountStatus;
import com.ignishers.backend.model.enums.RoleName;
import com.ignishers.backend.model.enums.StudentStatus;
import com.ignishers.backend.model.organization.Department;
import com.ignishers.backend.model.organization.Program;
import com.ignishers.backend.model.user.Role;
import com.ignishers.backend.model.user.Student;
import com.ignishers.backend.model.user.User;
import com.ignishers.backend.model.user.UserRole;
import com.ignishers.backend.repository.academic.CurriculumRepository;
import com.ignishers.backend.repository.organization.DepartmentRepository;
import com.ignishers.backend.repository.organization.ProgramRepository;
import com.ignishers.backend.repository.user.RoleRepository;
import com.ignishers.backend.repository.user.StudentRepository;
import com.ignishers.backend.repository.user.UserRepository;
import com.ignishers.backend.service.StudentRegistrationService;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Service
public class StudentRegistrationServiceImpl implements StudentRegistrationService {

    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final StudentRepository studentRepository;
    private final DepartmentRepository departmentRepository;
    private final ProgramRepository programRepository;
    private final CurriculumRepository curriculumRepository;
    private final PasswordEncoder passwordEncoder;
    private final AuthServiceImpl authService;

    @Override
    @Transactional
    public AuthResponse registerStudent(StudentRegisterRequest request) {
        validateEmailNotTaken(request.email());

        User user = createUser(request);
        assignStudentRole(user);

        Student student = createStudentProfile(user, request);

        userRepository.save(user);
        studentRepository.save(student);

        return authService.buildAuthResponse(user);
    }

    private void validateEmailNotTaken(@NotBlank(message = "Email is required") @Email(message = "Email must be valid") String email) {
        if (userRepository.existsByEmail(email)) {
            throw new EmailAlreadyExistsException(email);
        }
    }

    private User createUser(StudentRegisterRequest request) {
        return User.builder()
                .email(request.email())
                .password(passwordEncoder.encode(request.password()))
                .accountStatus(AccountStatus.PENDING)
                .build();
    }

    private void assignStudentRole(User user) {
        Role studentRole =  roleRepository.findByRoleName(RoleName.STUDENT)
                .orElseThrow(() -> new IllegalStateException("STUDENT role not seeded"));

        UserRole userRole = UserRole.builder()
                .user(user)
                .role(studentRole)
                .build();

        user.addRole(userRole);

    }

    private Student createStudentProfile(User user, StudentRegisterRequest request) {
        Department department = departmentRepository.findById(request.departmentId())
                .orElseThrow(() -> new IllegalStateException("Department Not Found!"));
        Program program = programRepository.findById(request.programId())
                .orElseThrow(() -> new IllegalStateException("Program Not Found!"));
        Curriculum curriculum = curriculumRepository.findById(request.curriculumId())
                .orElseThrow(() -> new IllegalStateException("Curriulum Not Found!"));

        return Student.builder()
                .user(user)
                .department(department)
                .program(program)
                .curriculum(curriculum)
                .name(request.name())
                .enrollmentNo(request.enrollmentNo())
                .admissionYear(request.admissionYear())
                .currentSemester(request.currentSemester())
                .studentStatus(StudentStatus.ENROLLED)
                .build();
    }
}
