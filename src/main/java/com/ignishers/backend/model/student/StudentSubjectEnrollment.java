package com.ignishers.backend.model.student;

import com.ignishers.backend.model.academic.Subject;
import com.ignishers.backend.model.enums.SubjectEnrollStatus;
import com.ignishers.backend.model.enums.SubjectEnrollType;
import com.ignishers.backend.model.attendance.StudentAttendance;
import com.ignishers.backend.model.common.BaseEntity;
import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.*;
import lombok.experimental.SuperBuilder;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter @Setter @NoArgsConstructor @SuperBuilder
@Table(name = "student_subject_enrollment", uniqueConstraints = @UniqueConstraint(
        name = "uk_student_sub_enroll",
        columnNames = {"student_sem_enrollment_id", "subject_id"}))
public class StudentSubjectEnrollment extends BaseEntity {

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "student_sem_enrollment_id", nullable = false)
    private StudentSemesterEnrollment studentSemesterEnrollment;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "subject_id", nullable = false)
    private Subject subject;

    @NotNull
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private SubjectEnrollType enrollmentType;
    // REGULAR, BACKLOG, ELECTIVE

    @NotNull
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    @Builder.Default
    private SubjectEnrollStatus status = SubjectEnrollStatus.ENROLLED;
    // ENROLLED, WITHDRAWN

    @OneToMany(mappedBy = "studentSubjectEnrollment",
            cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @Builder.Default
    private List<StudentAttendance> attendances = new ArrayList<>();
}