package com.ignishers.backend.repository.organization;

import com.ignishers.backend.model.organization.Program;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProgramRepository extends JpaRepository<Program, Long> {
}