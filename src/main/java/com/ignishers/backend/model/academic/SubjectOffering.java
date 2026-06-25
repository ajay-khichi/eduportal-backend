package com.ignishers.backend.model.academic;

import com.ignishers.backend.model.attendance.AttendanceSession;
import com.ignishers.backend.model.common.BaseEntity;
import com.ignishers.backend.model.section.Section;
import com.ignishers.backend.model.user.Faculty;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.SuperBuilder;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter @Setter @NoArgsConstructor @SuperBuilder
@Table(name = "subject_offering", uniqueConstraints = @UniqueConstraint(
        name = "uk_subject_offering",
        columnNames = {"faculty_id", "subject_id", "section_id",
                "semester_id", "session_id"}))
public class SubjectOffering extends BaseEntity {

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "faculty_id", nullable = false)
    private Faculty faculty;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "subject_id", nullable = false)
    private Subject subject;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "section_id", nullable = false)
    private Section section;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "semester_id", nullable = false)
    private Semester semester;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "session_id", nullable = false)
    private AcademicSession academicSession;

    @Builder.Default
    @OneToMany(mappedBy = "subjectOffering", cascade = CascadeType.ALL,
            orphanRemoval = true, fetch = FetchType.LAZY)
    private List<AttendanceSession> attendanceSessions = new ArrayList<>();

    public void addAttendanceSession(AttendanceSession as) {
        attendanceSessions.add(as); as.setSubjectOffering(this);
    }
}