package com.ignishers.backend.model.attendance;

import com.ignishers.backend.model.student.StudentSubjectEnrollment;
import com.ignishers.backend.model.common.BaseEntity;
import com.ignishers.backend.model.enums.AttendanceStatus;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@Entity
@Table(name = "student_attendance")
public class StudentAttendance extends BaseEntity {
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "attendance_session_id", nullable = false)
    private AttendanceSession attendanceSession;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "student_subject_enrollment_id", nullable = false)
    private StudentSubjectEnrollment studentSubjectEnrollment;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "attendance_verification_id")
    private AttendanceVerification attendanceVerification;

    @Enumerated(EnumType.STRING)
    @Column(name = "attendance_status", nullable = false)
    private AttendanceStatus attendanceStatus;

    @Column(name = "marked_at", nullable = false)
    private LocalDateTime markedAt;

}