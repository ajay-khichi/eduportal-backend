package com.ignishers.backend.model.academic;

import com.ignishers.backend.model.common.BaseEntity;
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

@NoArgsConstructor
@SuperBuilder
@Getter
@Setter
@Entity
@Table(name = "elective_group")
public class ElectiveGroup extends BaseEntity {

    @NotBlank(message = "Name can't be Empty")
    @Column(nullable = false, unique = true)
    private String name;

    @NotBlank(message = "Elective group type is required")
    @Column(nullable = false)
    private String type;

    @NotNull(message = "Min selection is required")
    @Min(value = 0, message = "Min selection can't be negative")
    @Column(nullable = false)
    private Integer minSelection;

    @OneToMany(
            mappedBy = "electiveGroup",
            cascade = CascadeType.ALL,
            orphanRemoval = true,
            fetch = FetchType.LAZY
    )
    private List<ElectiveSubject> electiveSubjects = new ArrayList<>();

    public void addElectiveSubject(ElectiveSubject es) {
        electiveSubjects.add(es);
        es.setElectiveGroup(this);
    }
}
