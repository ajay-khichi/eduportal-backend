package com.ignishers.backend.model.attendance;

import com.ignishers.backend.model.common.BaseEntity;
import com.ignishers.backend.model.organization.Classroom;
import com.ignishers.backend.model.academic.SubjectOffering;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.time.LocalTime;

@Getter
@Setter
@Entity
@Table(
        name = "attendance_session",
        uniqueConstraints = @UniqueConstraint(
                columnNames = {"subject_offering_id", "session_date", "start_time"}
        )
)
public class AttendanceSession extends BaseEntity {
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "subject_offering_id", nullable = false)
    private SubjectOffering subjectOffering;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "classroom_id", nullable = false)
    private Classroom classroom;

    @Column(name = "session_date", nullable = false)
    private LocalDate sessionDate;

    @Column(name = "start_time", nullable = false)
    private LocalTime startTime;

    @Column(name = "end_time", nullable = false)
    private LocalTime endTime;

}