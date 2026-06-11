package com.ignishers.backend.repository.academic;


import com.ignishers.backend.model.academic.SubjectOffering;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SubjectOfferingRepository extends JpaRepository<SubjectOffering, Long> {
}
