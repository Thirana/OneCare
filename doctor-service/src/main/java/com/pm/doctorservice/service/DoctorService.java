package com.pm.doctorservice.service;

import com.pm.doctorservice.dto.AvailabilityRequestDto;
import com.pm.doctorservice.dto.AvailabilityResponseDto;
import com.pm.doctorservice.dto.DoctorRequestDTO;
import com.pm.doctorservice.dto.DoctorResponseDTO;
import com.pm.doctorservice.model.Availability;

import java.util.List;
import java.util.UUID;

public interface DoctorService {
    DoctorResponseDTO createDoctor(DoctorRequestDTO dto);

    DoctorResponseDTO getDoctorById(UUID id);

    List<DoctorResponseDTO> getAllDoctors();

    DoctorResponseDTO updateDoctor(UUID id, DoctorRequestDTO dto);

    void deleteDoctor(UUID id);

    List<DoctorResponseDTO> getDoctorsBySpecialization(String specialization);

    List<AvailabilityResponseDto> getDoctorAvailability(UUID doctorId);

    AvailabilityResponseDto addAvailability(UUID doctorId, AvailabilityRequestDto dto);

    void deleteAvailability(UUID doctorId, UUID availabilityId);

}
