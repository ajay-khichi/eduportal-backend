package com.ignishers.backend.model.organization;

import com.ignishers.backend.model.common.BaseEntity;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
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
@Table(name = "department")
public class Department extends BaseEntity {

    @NotBlank(message = "Department name is required")
    @Column(nullable = false)
    private String name;

    @NotBlank(message = "Department Code is required")
    @Column(nullable = false, unique = true)
    private String code;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "college_id", nullable = false)
    private College college;

    @OneToMany(
            mappedBy = "department",
            fetch = FetchType.LAZY,
            cascade = CascadeType.ALL,
            orphanRemoval = true
    )
    private List<Program> programs = new ArrayList<>();

//----------------------------------------------------------------------------------------
//----------------------------------------------------------------------------------------
    // helper — dono sides sync karta hai
    public void addProgram(Program program){
        programs.add(program);
        program.setDepartment(this);
    }
    public void removeProgram(Program program){
        programs.remove(program);
        program.setDepartment(null);
    }
}
