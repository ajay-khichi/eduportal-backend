package com.ignishers.backend.repository.biometric;

import com.ignishers.backend.model.biometric.StudentFaceData;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface StudentFaceDataRepository extends JpaRepository<StudentFaceData, Long> {

}
