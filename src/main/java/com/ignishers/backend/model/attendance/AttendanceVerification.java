package com.ignishers.backend.model.attendance;

import com.ignishers.backend.model.common.BaseEntity;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

@Getter
@Setter
@NoArgsConstructor
@SuperBuilder
@Entity
@Table(name = "attendance_verification")
public class AttendanceVerification extends BaseEntity {
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "student_attendance_id", nullable = false)
    private StudentAttendance studentAttendance;

    @Column(name = "qr_verified", nullable = false)
    private Boolean qrVerified = false;

    @Column(name = "location_verified", nullable = false)
    private Boolean locationVerified = false;

    @Column(name = "face_verified", nullable = false)
    private Boolean faceVerified = false;

    @Column(name = "failure_reason")
    private String failureReason;

}