package com.ignishers.backend.repository.student;

import com.ignishers.backend.model.student.StudentSemesterEnrollment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface StudentSemesterEnrollmentRepository extends JpaRepository<StudentSemesterEnrollment, Long> {
}