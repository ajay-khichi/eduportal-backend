package com.ignishers.backend.model.section;

import com.ignishers.backend.model.academic.Semester;
import com.ignishers.backend.model.student.StudentSection;
import com.ignishers.backend.model.academic.SubjectOffering;
import com.ignishers.backend.model.academic.AcademicSession;
import com.ignishers.backend.model.common.BaseEntity;
import com.ignishers.backend.model.organization.Program;
import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.*;
import lombok.experimental.SuperBuilder;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter @Setter @NoArgsConstructor @SuperBuilder
@Table(name = "section", uniqueConstraints = @UniqueConstraint(
        name = "uk_section",
        columnNames = {"program_id", "semester_id", "session_id", "name"}))
public class Section extends BaseEntity {

    @NotBlank(message = "Section name is required")
    @Column(nullable = false)
    private String name;             // e.g. "A", "B"

    private Integer capacity;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "program_id", nullable = false)
    private Program program;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "semester_id", nullable = false)
    private Semester semester;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "session_id", nullable = false)
    private AcademicSession academicSession;

    @OneToMany(mappedBy = "section", cascade = CascadeType.ALL,
            orphanRemoval = true, fetch = FetchType.LAZY)
    @Builder.Default
    private List<StudentSection> studentSections = new ArrayList<>();

    @OneToMany(mappedBy = "section", cascade = CascadeType.ALL,
            fetch = FetchType.LAZY)
    @Builder.Default
    private List<SubjectOffering> subjectOfferings = new ArrayList<>();

    public void addStudentSection(StudentSection ss) {
        studentSections.add(ss); ss.setSection(this);
    }
}