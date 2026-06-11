package com.ignishers.backend.repository.academic;


import com.ignishers.backend.model.academic.AcademicSession;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AcademicSessionRepository extends JpaRepository<AcademicSession, Long> {
}
