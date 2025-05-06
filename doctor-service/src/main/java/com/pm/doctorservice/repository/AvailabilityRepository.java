package com.pm.doctorservice.repository;


import com.pm.doctorservice.model.Availability;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.UUID;

public interface AvailabilityRepository extends JpaRepository<Availability, UUID> {
    List<Availability> findByDoctorId(UUID doctorId);
}
