package com.ignishers.backend.model.academic;

import com.ignishers.backend.model.common.BaseEntity;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.*;
import lombok.experimental.SuperBuilder;

import java.time.LocalDate;

@Entity
@Getter
@Setter
@NoArgsConstructor
@SuperBuilder
@Table(name = "academic_session")
public class AcademicSession extends BaseEntity {

    @NotBlank(message = "Academic Session name is required")
    @Column(nullable = false, unique = true)
    private String name;

    @NotNull(message = "Start date is required")
    @Column(nullable = false)
    private LocalDate startDate;

    @NotNull(message = "End date is required")
    @Column(nullable = false)
    private LocalDate endDate;

    @Column(nullable = false)
    private Boolean isActive = false;  // current vs past session
}
