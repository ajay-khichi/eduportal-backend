package com.ignishers.backend.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.*;
import lombok.experimental.SuperBuilder;

import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Setter
@NoArgsConstructor
@SuperBuilder
@Table(name = "institution")
public class Institution extends BaseEntity {

    @NotBlank(message = "Institution name is required")
    @Column(nullable = false)
    private String name;

    private String type;

    @OneToMany(
            mappedBy = "institution",
            fetch = FetchType.LAZY,
            cascade = CascadeType.ALL,
            orphanRemoval = true   // college ka institution ke bina koi existence nahi
    )
    private List<College> colleges = new ArrayList<>();


//----------------------------------------------------------------------------------------
//----------------------------------------------------------------------------------------
    // helper — dono sides sync karta hai
    public void addCollege(College college) {
        colleges.add(college);
        college.setInstitution(this);
    }

    public void removeCollege(College college) {
        colleges.remove(college);
        college.setInstitution(null);
    }
}