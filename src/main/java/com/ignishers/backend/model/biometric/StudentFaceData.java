package com.ignishers.backend.model.biometric;

import com.ignishers.backend.model.common.BaseEntity;
import com.ignishers.backend.model.user.Student;
import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.*;
import lombok.experimental.SuperBuilder;
import java.time.LocalDateTime;

@Entity
@Getter @Setter @NoArgsConstructor @SuperBuilder
@Table(name = "student_face_data")
public class StudentFaceData extends BaseEntity {

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "student_id", nullable = false, unique = true)
    private Student student;

    // encrypted face embedding — byte array
    @NotNull
    @Column(nullable = false, columnDefinition = "BYTEA")
    private byte[] faceEmbedding;

    @NotNull @Column(nullable = false)
    private LocalDateTime registeredAt;

    @NotNull @Column(nullable = false)
    private LocalDateTime lastUpdated;
}