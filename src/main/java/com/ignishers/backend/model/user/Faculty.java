package com.ignishers.backend.model.user;

import com.ignishers.backend.model.academic.SubjectOffering;
import com.ignishers.backend.model.common.BaseEntity;
import com.ignishers.backend.model.enums.FacultyStatus;
import com.ignishers.backend.model.organization.Department;
import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.*;
import lombok.experimental.SuperBuilder;

import java.util.ArrayList;
import java.util.List;

@SuperBuilder
@NoArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "faculty")
public class Faculty extends BaseEntity {

    // OneToOne — ek user ka ek hi faculty profile
    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false, unique = true)
    private User user;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "department_id", nullable = false)
    private Department department;

    @NotBlank(message = "Faculty name is required")
    @Column(nullable = false)
    private String name;

    @NotBlank(message = "Employee code is required")
    @Column(nullable = false, unique = true)
    private String employeeCode;

    // nullable — e.g. "Asst Prof", "Prof" — optional
    private String designation;

    @NotBlank(message = "Faculty status is required")
    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private FacultyStatus facultyStatus = FacultyStatus.ACTIVE;
    // e.g. "Active", "Retired"

    @OneToMany(mappedBy = "faculty", cascade = CascadeType.ALL,
            fetch = FetchType.LAZY)
    private List<SubjectOffering> subjectOfferings = new ArrayList<>();

    public void addSubjectOffering(SubjectOffering so) {
        subjectOfferings.add(so);
        so.setFaculty(this);
    }
}