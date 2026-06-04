package com.ignishers.backend.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

@Entity
@Getter
@Setter
@NoArgsConstructor
@SuperBuilder
@Table(name = "subject")
public class Subject extends BaseEntity{

    @NotBlank(message = "Subject Code is required")
    @Column(nullable = false, unique = true)
    private String subjectCode;

    @NotBlank(message = "Subject Name is required")
    @Column(nullable = false)
    private String subjectName;

    @NotNull(message = "Credits are required")
    @Min(value = 1, message = "Credits must be at least 1")
    @Column(nullable = false)
    private Integer credits;

    @NotNull(message = "Subject type is required")
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private SubjectType subjectType;


}
