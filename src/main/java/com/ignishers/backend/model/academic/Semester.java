package com.ignishers.backend.model.academic;

import com.ignishers.backend.model.common.BaseEntity;
import jakarta.persistence.*;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Setter
@NoArgsConstructor
@SuperBuilder
@Table(
        name = "semester",
        uniqueConstraints = @UniqueConstraint(
                name = "uk_semester_number_curriculum",
                columnNames = {"semester_number", "curriculum_id"}
        )
)
public class Semester extends BaseEntity {

    @Column(nullable = false)
    @Min(value = 1, message = "Semester number must be at least 1")
    @NotNull(message = "Semester Number is required")
    private Integer semesterNumber;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "curriculum_id", nullable = false)
    private Curriculum curriculum;

    @OneToMany(
            mappedBy = "semester",
            cascade = CascadeType.ALL,
            orphanRemoval = true,
            fetch = FetchType.LAZY
    )
    @Builder.Default
    private List<SemesterSubject> semesterSubjects = new ArrayList<>();

    public void addSemesterSubject(SemesterSubject ss) {
        semesterSubjects.add(ss);
        ss.setSemester(this);
    }
}
