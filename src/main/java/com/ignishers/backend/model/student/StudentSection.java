package com.ignishers.backend.model.student;

import com.ignishers.backend.model.academic.AcademicSession;
import com.ignishers.backend.model.common.BaseEntity;
import com.ignishers.backend.model.section.Section;
import com.ignishers.backend.model.user.Student;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.SuperBuilder;

@Entity
@Getter @Setter @NoArgsConstructor @SuperBuilder
@Table(name = "student_section", uniqueConstraints = @UniqueConstraint(
        name = "uk_student_section",
        columnNames = {"student_id", "section_id", "session_id"}))
public class StudentSection extends BaseEntity {

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "student_id", nullable = false)
    private Student student;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "section_id", nullable = false)
    private Section section;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "session_id", nullable = false)
    private AcademicSession academicSession;
}