package com.ignishers.backend.model.student;

import com.ignishers.backend.model.academic.AcademicSession;
import com.ignishers.backend.model.academic.Semester;
import com.ignishers.backend.model.common.BaseEntity;
import com.ignishers.backend.model.enums.SemEnrollStatus;
import com.ignishers.backend.model.user.Student;
import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.*;
import lombok.experimental.SuperBuilder;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter @Setter @NoArgsConstructor @SuperBuilder
@Table(name = "student_semester_enrollment", uniqueConstraints = @UniqueConstraint(
        name = "uk_student_sem_enroll",
        columnNames = {"student_id", "semester_id", "session_id"}))
public class StudentSemesterEnrollment extends BaseEntity {

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "student_id", nullable = false)
    private Student student;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "semester_id", nullable = false)
    private Semester semester;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "session_id", nullable = false)
    private AcademicSession academicSession;

    @NotNull(message = "Enrollment date is required")
    @Column(nullable = false)
    private LocalDate enrollmentDate;

    @NotNull
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private SemEnrollStatus status = SemEnrollStatus.ACTIVE;
    // ACTIVE, BACKLOG, PROMOTED

    @OneToMany(mappedBy = "studentSemesterEnrollment",
            cascade = CascadeType.ALL, orphanRemoval = true,
            fetch = FetchType.LAZY)
    private List<StudentSubjectEnrollment> subjectEnrollments = new ArrayList<>();

    public void addSubjectEnrollment(StudentSubjectEnrollment sse) {
        subjectEnrollments.add(sse);
        sse.setStudentSemesterEnrollment(this);
    }
}