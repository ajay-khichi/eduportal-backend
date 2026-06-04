package com.ignishers.backend.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.*;
import lombok.experimental.SuperBuilder;

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
    private Users user;

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
    private String status;
    // e.g. "Active", "Retired"
}