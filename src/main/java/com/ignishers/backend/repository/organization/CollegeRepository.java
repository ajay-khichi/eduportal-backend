package com.ignishers.backend.repository.organization;

import com.ignishers.backend.model.organization.College;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CollegeRepository extends JpaRepository<College, Long> {
}