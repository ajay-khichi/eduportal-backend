package com.ignishers.backend.repository.attendance;


import com.ignishers.backend.model.attendance.AttendanceVerification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AttendanceVerificationRepository extends JpaRepository<AttendanceVerification, Long> {
}
