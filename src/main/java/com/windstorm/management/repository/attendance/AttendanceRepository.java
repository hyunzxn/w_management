package com.windstorm.management.repository.attendance;

import org.springframework.data.jpa.repository.JpaRepository;

import com.windstorm.management.domain.attendance.Attendance;

public interface AttendanceRepository extends JpaRepository<Attendance, Long> {
}
