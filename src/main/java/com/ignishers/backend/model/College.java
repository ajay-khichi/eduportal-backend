package com.ignishers.backend.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.*;
import lombok.experimental.SuperBuilder;

import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Setter
@NoArgsConstructor
@SuperBuilder
@Table(name = "college")
public class College extends BaseEntity{

    @ManyToOne(fetch = FetchType.LAZY)   // LAZY — default EAGER avoid karo
    @JoinColumn(name = "institution_id", nullable = false)
    private Institution institution;

    @NotBlank(message = "College name is required")
    @Column(nullable = false)
    private String name;

    @OneToMany(
            mappedBy = "college",
            fetch = FetchType.LAZY,
            cascade = CascadeType.ALL,
            orphanRemoval = true
    )
    private List<Department> departments = new ArrayList<>();

//    -----------------------------------------------------------------------------
//    --------------------------------------------------------------------------------

//    helper methods
    public void addDepartment(Department department) {
        departments.add(department);
        department.setCollege(this);
    }

    public void removeDepartment(Department department) {
        departments.remove(department);
        department.setCollege(null);
    }


}
