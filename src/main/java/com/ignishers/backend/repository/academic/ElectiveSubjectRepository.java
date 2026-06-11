package com.ignishers.backend.repository.academic;


import com.ignishers.backend.model.academic.ElectiveSubject;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ElectiveSubjectRepository extends JpaRepository<ElectiveSubject, Long> {
}
