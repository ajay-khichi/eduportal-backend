package com.ignishers.backend.model.organization;

import com.ignishers.backend.model.common.BaseEntity;
import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.*;
import lombok.experimental.SuperBuilder;
import java.math.BigDecimal;

@Entity
@Getter @Setter @NoArgsConstructor @SuperBuilder
@Table(name = "classroom", uniqueConstraints = @UniqueConstraint(
        name = "uk_classroom", columnNames = {"college_id", "room_number"}))
public class Classroom extends BaseEntity {

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "college_id", nullable = false)
    private College college;

    @NotBlank(message = "Room number is required")
    @Column(nullable = false)
    private String roomNumber;

    private String buildingName;

    @NotNull
    @Column(nullable = false, precision = 9, scale = 6)
    private BigDecimal latitude;

    @NotNull
    @Column(nullable = false, precision = 9, scale = 6)
    private BigDecimal longitude;

    @Column(nullable = false)
    private Integer allowedRadiusMeters = 50;
}