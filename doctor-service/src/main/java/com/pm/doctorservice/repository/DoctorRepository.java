package com.pm.doctorservice.repository;


import com.pm.doctorservice.model.Doctor;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.UUID;

public interface DoctorRepository extends JpaRepository<Doctor, UUID> {
    List<Doctor> findBySpecialization(String specialization);
}