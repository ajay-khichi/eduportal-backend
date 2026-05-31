package com.ignishers.backend.model;

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

@Entity
@Getter
@Setter
@NoArgsConstructor
@SuperBuilder
@Table(name = "program")
public class Program extends BaseEntity{

    @NotBlank(message = "Program name is required")
    @Column(nullable = false)
    private String name;

    @NotBlank(message = "Degree Type is required")
    @Column(nullable = false)
    private String degreeType;

    @NotNull(message = "Program Duration is required")
    @Column(nullable = false)
    @Min(value = 1, message = "Duration must be at least 1 semester")
    private Integer durationSemesters;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "department_id", nullable = false)
    private Department department;

    @OneToMany(
            mappedBy = "program",
            fetch = FetchType.LAZY,
            cascade = CascadeType.ALL,
            orphanRemoval = true)
    private List<Curriculum> curriculums = new ArrayList<>();

    public void addCurriculum(Curriculum curriculum) {
        curriculums.add(curriculum);
        curriculum.setProgram(this);
    }
}
