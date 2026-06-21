package com.ignishers.backend.config;

import com.ignishers.backend.model.user.*;
import com.ignishers.backend.model.organization.*;
import com.ignishers.backend.model.academic.*;
import com.ignishers.backend.model.section.*;
import com.ignishers.backend.model.student.*;
import com.ignishers.backend.model.attendance.*;
import com.ignishers.backend.model.attendance.log.*;
import com.ignishers.backend.model.biometric.*;
import com.ignishers.backend.model.enums.*;

import com.ignishers.backend.repository.academic.*;
import com.ignishers.backend.repository.attendance.*;
import com.ignishers.backend.repository.attendance.log.*;
import com.ignishers.backend.repository.biometric.*;
import com.ignishers.backend.repository.organization.*;
import com.ignishers.backend.repository.section.*;
import com.ignishers.backend.repository.student.*;
import com.ignishers.backend.repository.user.*;

import lombok.RequiredArgsConstructor;
import org.jspecify.annotations.NonNull;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

import java.time.*;
import java.math.BigDecimal;

@Component
@RequiredArgsConstructor
@Profile("dev")
public class DataSeeder implements CommandLineRunner {

    private final AcademicSessionRepository academicSessionRepository;
    private final CurriculumRepository curriculumRepository;
    private final ElectiveGroupRepository electiveGroupRepository;
    private final ElectiveSubjectRepository electiveSubjectRepository;
    private final SemesterRepository semesterRepository;
    private final SemesterSubjectRepository semesterSubjectRepository;
    private final SubjectOfferingRepository subjectOfferingRepository;
    private final SubjectRepository subjectRepository;
    private final AttendanceSessionRepository attendanceSessionRepository;
    private final AttendanceVerificationRepository attendanceVerificationRepository;
    private final StudentAttendanceRepository studentAttendanceRepository;
    private final AttendanceAuditLogRepository attendanceAuditLogRepository;
    private final AttendanceLocationLogRepository attendanceLocationLogRepository;
    private final FaceVerificationLogRepository faceVerificationLogRepository;
    private final StudentFaceDataRepository studentFaceDataRepository;
    private final ClassroomRepository classroomRepository;
    private final CollegeRepository collegeRepository;
    private final DepartmentRepository departmentRepository;
    private final InstitutionRepository institutionRepository;
    private final ProgramRepository programRepository;
    private final SectionRepository sectionRepository;
    private final StudentSectionRepository studentSectionRepository;
    private final StudentSemesterEnrollmentRepository studentSemesterEnrollmentRepository;
    private final StudentSubjectEnrollmentRepository studentSubjectEnrollmentRepository;
    private final FacultyRepository facultyRepository;
    private final RoleRepository roleRepository;
    private final StudentRepository studentRepository;
    private final UserRepository userRepository;
    private final UserRoleRepository userRoleRepository;

    @Override
    public void run( String @NonNull ... args) {
        if (roleRepository.count() == 0) {
            seedData();
        }
    }

    private void seedData() {

        String timezone = "Asia/Kolkata";
        // 1. Roles
        for (RoleName name : RoleName.values()) {
            if (!roleRepository.existsByRoleName(name)) {
                roleRepository.save(Role.builder().roleName(name).build());
            }
        }

        Role superAdminRole = roleRepository.findByRoleName(RoleName.SUPER_ADMIN)
                .orElseThrow(() -> new RuntimeException("Role not found: SUPER_ADMIN"));
        Role studentRole = roleRepository.findByRoleName(RoleName.STUDENT)
                .orElseThrow(() -> new RuntimeException("Role not found: STUDENT"));
        Role facultyRole = roleRepository.findByRoleName(RoleName.FACULTY)
                .orElseThrow(() -> new RuntimeException("Role not found: FACULTY"));

        // 2. Organization
        Institution inst = institutionRepository.save(Institution.builder()
                .name("Chameli Devi Group of Institutions")
                .type("COLLEGE")
                .build());

        College college = collegeRepository.save(College.builder()
                .name("Chameli Devi Group of Institutions")
                .institution(inst)
                .build());

        Department dept = departmentRepository.save(Department.builder()
                .name("Computer Science and Engineering")
                .code("CS")
                .college(college)
                .build());

        Program program = programRepository.save(Program.builder()
                .department(dept)
                .name("BTech CSE")
                .degreeType("Regular")
                .durationSemesters(8)
                .build());

        Classroom classroom = classroomRepository.save(Classroom.builder()
                .college(college)
                .roomNumber("101")
                .latitude(new BigDecimal("22.123456"))
                .longitude(new BigDecimal("75.123456"))
                .allowedRadiusMeters(50)
                .build());

        // 3. Academic
        AcademicSession session = academicSessionRepository.save(AcademicSession.builder()
                .name("2023-2024")
                .startDate(LocalDate.of(2023, Month.JULY, 1))
                .endDate(LocalDate.of(2024, Month.JUNE, 30))
                .isActive(true)
                .build());

        Curriculum curr = curriculumRepository.save(Curriculum.builder()
                .version(1)
                .effectiveFrom(LocalDate.of(2023, Month.JULY, 1))
                .program(program)
                .academicSession(session)
                .build());

        Semester sem = semesterRepository.save(Semester.builder()
                .semesterNumber(1)
                .curriculum(curr)
                .build());

        Subject sub1 = subjectRepository.save(Subject.builder()
                .subjectCode("CS101")
                .subjectName("Intro to CS")
                .credits(4)
                .subjectType(SubjectType.THEORY)
                .build());

        semesterSubjectRepository.save(SemesterSubject.builder()
                .semester(sem)
                .subject(sub1)
                .isElective(false)
                .build());

        ElectiveGroup eg = electiveGroupRepository.save(ElectiveGroup.builder()
                .name("Group A")
                .type("DEPT_ELECTIVE")
                .minSelection(1)
                .build());

        electiveSubjectRepository.save(ElectiveSubject.builder()
                .electiveGroup(eg)
                .subject(sub1)
                .build());

        // 4. Section
        Section section = sectionRepository.save(Section.builder()
                .name("A")
                .program(program)
                .semester(sem)
                .academicSession(session)
                .capacity(60)
                .build());

        // 5. Users
        User adminUser = userRepository.save(User.builder()
                .email("admin@test.com")
                .password("$2y$12$DcyzxMDmNJEtGOB9nzsg9.EO1HlCof5I4D1zjyRB9ES7lBCqeCHDK")
                .accountStatus(AccountStatus.ACTIVE)
                .build());

        userRoleRepository.save(UserRole.builder()
                .user(adminUser)
                .role(superAdminRole)
                .build());

        User studentUser = userRepository.save(User.builder()
                .email("student@test.com")
                .password("hash")
                .accountStatus(AccountStatus.ACTIVE)
                .build());

        userRoleRepository.save(UserRole.builder()
                .user(studentUser)
                .role(studentRole)
                .build());

        Student student = studentRepository.save(Student.builder()
                .user(studentUser)
                .department(dept)
                .program(program)
                .curriculum(curr)
                .name("John Doe")
                .enrollmentNo("0801CS201001")
                .admissionYear(2020)
                .currentSemester(1)
                .studentStatus(StudentStatus.ENROLLED)
                .build());

        User facultyUser = userRepository.save(User.builder()
                .email("faculty@test.com")
                .password("hash")
                .accountStatus(AccountStatus.ACTIVE)
                .build());

        userRoleRepository.save(UserRole.builder()
                .user(facultyUser)
                .role(facultyRole)
                .build());

        Faculty faculty = facultyRepository.save(Faculty.builder()
                .user(facultyUser)
                .department(dept)
                .name("Jane Doe")
                .employeeCode("EMP01")
                .status("Active")
                .build());

        // 6. Student Enrollments & Section
        studentSectionRepository.save(StudentSection.builder()
                .student(student)
                .section(section)
                .academicSession(session)
                .build());

        StudentSemesterEnrollment sse = studentSemesterEnrollmentRepository.save(
                StudentSemesterEnrollment.builder()
                        .student(student)
                        .semester(sem)
                        .academicSession(session)
                        .enrollmentDate(LocalDate.now(ZoneId.of(timezone)))
                        .status(SemEnrollStatus.ACTIVE)
                        .build());

        StudentSubjectEnrollment studentSubjectEnrollment = studentSubjectEnrollmentRepository.save(
                StudentSubjectEnrollment.builder()
                        .studentSemesterEnrollment(sse)
                        .subject(sub1)
                        .enrollmentType(SubjectEnrollType.REGULAR)
                        .status(SubjectEnrollStatus.ENROLLED)
                        .build());

        // 7. Subject Offering
        SubjectOffering so = subjectOfferingRepository.save(SubjectOffering.builder()
                .faculty(faculty)
                .subject(sub1)
                .section(section)
                .semester(sem)
                .academicSession(session)
                .build());

        // 8. Attendance Session
        AttendanceSession attSession = attendanceSessionRepository.save(
                AttendanceSession.builder()
                        .subjectOffering(so)
                        .classroom(classroom)
                        .sessionDate(LocalDate.now(ZoneId.of(timezone)))
                        .startTime(LocalTime.of(10, 0))
                        .endTime(LocalTime.of(11, 0))
                        .build());

        StudentAttendance stAtt = studentAttendanceRepository.save(
                StudentAttendance.builder()
                        .attendanceSession(attSession)
                        .studentSubjectEnrollment(studentSubjectEnrollment)
                        .attendanceStatus(AttendanceStatus.PRESENT)
                        .markedAt(LocalDateTime.now(ZoneId.of(timezone)))
                        .build());

        AttendanceVerification attVer = attendanceVerificationRepository.save(
                AttendanceVerification.builder()
                        .studentAttendance(stAtt)
                        .qrVerified(true)
                        .locationVerified(true)
                        .faceVerified(true)
                        .build());

        attendanceAuditLogRepository.save(AttendanceAuditLog.builder()
                .attendanceSession(attSession)
                .student(student)
                .action("CREATED")
                .timestamp(LocalDateTime.now(ZoneId.of(timezone)))
                .build());

        faceVerificationLogRepository.save(FaceVerificationLog.builder()
                .attendanceVerification(attVer)
                .confidenceScore(new BigDecimal("0.999"))
                .matched(true)
                .capturedAt(LocalDateTime.now(ZoneId.of(timezone)))
                .build());

        attendanceLocationLogRepository.save(AttendanceLocationLog.builder()
                .attendanceVerification(attVer)
                .latitude(new BigDecimal("22.123456"))
                .longitude(new BigDecimal("75.123456"))
                .distanceFromClassroom(10)
                .capturedAt(LocalDateTime.now(ZoneId.of(timezone)))
                .build());

        // 9. Biometrics
        studentFaceDataRepository.save(StudentFaceData.builder()
                .student(student)
                .faceEmbedding(new byte[]{1, 2, 3})
                .registeredAt(LocalDateTime.now(ZoneId.of(timezone)))
                .lastUpdated(LocalDateTime.now(ZoneId.of(timezone)))
                .build());
    }
}