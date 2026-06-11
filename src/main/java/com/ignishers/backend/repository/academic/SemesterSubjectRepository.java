package com.ignishers.backend.repository.academic;


import com.ignishers.backend.model.academic.SemesterSubject;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SemesterSubjectRepository extends JpaRepository<SemesterSubject, Long> {
}
