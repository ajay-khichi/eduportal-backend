package com.ignishers.backend.model.academic;

import com.ignishers.backend.model.common.BaseEntity;
import com.ignishers.backend.model.organization.Program;
import jakarta.persistence.*;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Setter
@NoArgsConstructor
@SuperBuilder
@Table(name = "curriculum")
public class Curriculum extends BaseEntity {

    @NotNull(message = "Version number is required")
    @Min(value = 1,message = "version cannot be negative")
    @Column(nullable = false)
    private Integer version;

    @NotNull(message = "In Effect date is required")
    @Column(nullable = false)
    private LocalDate effectiveFrom;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "program_id", nullable = false)
    private Program program;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "session_id", nullable = false)
    private AcademicSession academicSession;

    @OneToMany(mappedBy = "curriculum", fetch = FetchType.LAZY, cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Semester> semesters = new ArrayList<>();

    public void addSemester(Semester semester) {
        semesters.add(semester);
        semester.setCurriculum(this);
    }


}
