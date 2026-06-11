package com.ignishers.backend.repository.student;

import com.ignishers.backend.model.student.StudentSection;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface StudentSectionRepository extends JpaRepository<StudentSection, Long> {
}