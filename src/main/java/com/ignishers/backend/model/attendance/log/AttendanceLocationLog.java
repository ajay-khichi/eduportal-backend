package com.ignishers.backend.model.attendance.log;

import com.ignishers.backend.model.attendance.AttendanceVerification;
import com.ignishers.backend.model.common.BaseEntity;
import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.*;
import lombok.experimental.SuperBuilder;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Getter @Setter @NoArgsConstructor @SuperBuilder
@Table(name = "attendance_location_log")
public class AttendanceLocationLog extends BaseEntity {

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "attendance_verification_id", nullable = false)
    private AttendanceVerification attendanceVerification;

    @NotNull @Column(nullable = false, precision = 9, scale = 6)
    private BigDecimal latitude;

    @NotNull @Column(nullable = false, precision = 9, scale = 6)
    private BigDecimal longitude;

    @NotNull @Column(nullable = false)
    private Integer distanceFromClassroom; // app mein compute hoga

    @NotNull @Column(nullable = false)
    private LocalDateTime capturedAt;
}