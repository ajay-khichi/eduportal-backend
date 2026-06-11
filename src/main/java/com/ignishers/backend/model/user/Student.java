package com.ignishers.backend.model.user;

import com.ignishers.backend.model.academic.Curriculum;
import com.ignishers.backend.model.biometric.StudentFaceData;
import com.ignishers.backend.model.common.BaseEntity;
import com.ignishers.backend.model.enums.StudentStatus;
import com.ignishers.backend.model.organization.Department;
import com.ignishers.backend.model.organization.Program;
import com.ignishers.backend.model.student.StudentSection;
import com.ignishers.backend.model.student.StudentSemesterEnrollment;
import jakarta.persistence.*;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

import java.util.ArrayList;
import java.util.List;

@SuperBuilder
@NoArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "student")
public class Student extends BaseEntity {

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false, unique = true)
    private User user;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "department_id", nullable = false)
    private Department department;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "program_id", nullable = false)
    private Program program;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "curriculum_id", nullable = false)
    private Curriculum curriculum;

    @NotBlank(message = "Student name is required")
    @Column(nullable = false)
    private String name;

    @NotBlank(message = "Enrollment number is required")
    @Column(nullable = false, unique = true)
    private String enrollmentNo;

    @NotNull(message = "Admission year is required")
    @Column(nullable = false)
    private Integer admissionYear;

    @NotNull(message = "Current semester is required")
    @Min(value = 1, message = "Semester must be at least 1")
    @Column(nullable = false)
    private Integer currentSemester;

    @NotNull(message = "Student status is required")
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private StudentStatus studentStatus = StudentStatus.ENROLLED;

    @OneToMany(mappedBy = "student", cascade = CascadeType.ALL,
            orphanRemoval = true, fetch = FetchType.LAZY)
    private List<StudentSection> studentSections = new ArrayList<>();

    @OneToMany(mappedBy = "student", cascade = CascadeType.ALL,
            orphanRemoval = true, fetch = FetchType.LAZY)
    private List<StudentSemesterEnrollment> semesterEnrollments = new ArrayList<>();

    @OneToOne(mappedBy = "student", cascade = CascadeType.ALL,
            fetch = FetchType.LAZY)
    private StudentFaceData faceData;


    public void addSemesterEnrollment(StudentSemesterEnrollment sse) {
        semesterEnrollments.add(sse);
        sse.setStudent(this);
    }
}