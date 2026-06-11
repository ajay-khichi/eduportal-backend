package com.ignishers.backend.repository.attendance.log;

import com.ignishers.backend.model.attendance.log.AttendanceLocationLog;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AttendanceLocationLogRepository extends JpaRepository<AttendanceLocationLog, Long> {
}