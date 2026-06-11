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
@Table(name = "face_verification_log")
public class FaceVerificationLog extends BaseEntity {

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "attendance_verification_id", nullable = false)
    private AttendanceVerification attendanceVerification;

    @NotNull
    @Column(nullable = false, precision = 5, scale = 4)
    private BigDecimal confidenceScore; // e.g. 0.9876

    @Column(nullable = false)
    private Boolean matched;

    @NotNull @Column(nullable = false)
    private LocalDateTime capturedAt;

    private String imagePath; // optional — agar image store kar rahe ho
}