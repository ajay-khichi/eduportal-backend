package com.ignishers.backend.model.academic;

import com.ignishers.backend.model.common.BaseEntity;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

@NoArgsConstructor
@SuperBuilder
@Getter
@Setter
@Entity
@Table(
        name = "semester_subject",
        uniqueConstraints = @UniqueConstraint(
                name = "uk_semester_subject",
                columnNames = {"semester_id", "subject_id"}
        )
)
public class SemesterSubject extends BaseEntity {

    @Column(nullable = false)
    private Boolean isElective = false;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "semester_id", nullable = false)
    private Semester semester;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "subject_id", nullable = false)
    private Subject subject;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "elective_group_id")
    private ElectiveGroup electiveGroup;
}
