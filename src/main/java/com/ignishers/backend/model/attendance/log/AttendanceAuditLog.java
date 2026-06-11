package com.ignishers.backend.model.attendance.log;

import com.ignishers.backend.model.user.Student;
import com.ignishers.backend.model.attendance.AttendanceSession;
import com.ignishers.backend.model.common.BaseEntity;
import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.*;
import lombok.experimental.SuperBuilder;
import java.time.LocalDateTime;

@Entity
@Getter @Setter @NoArgsConstructor @SuperBuilder
@Table(name = "attendance_audit_log")
public class AttendanceAuditLog extends BaseEntity {

    // both nullable — log kisi ek ya dono se linked ho sakta hai
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "attendance_session_id")
    private AttendanceSession attendanceSession;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "student_id")
    private Student student;

    @NotBlank
    @Column(nullable = false)
    private String action;
    // "QR Scan", "Face Fail", "GPS Reject" etc.

    private String ipAddress;
    private String deviceInfo;

    @NotNull @Column(nullable = false)
    private LocalDateTime timestamp;
}