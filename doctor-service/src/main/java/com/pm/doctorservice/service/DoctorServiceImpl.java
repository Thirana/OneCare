package com.pm.doctorservice.service;

import com.pm.doctorservice.dto.AvailabilityRequestDto;
import com.pm.doctorservice.dto.AvailabilityResponseDto;
import com.pm.doctorservice.dto.DoctorRequestDTO;
import com.pm.doctorservice.dto.DoctorResponseDTO;
import com.pm.doctorservice.mapper.AvailabilityMapper;
import com.pm.doctorservice.mapper.DoctorMapper;
import com.pm.doctorservice.model.Availability;
import com.pm.doctorservice.model.Doctor;
import com.pm.doctorservice.repository.AvailabilityRepository;
import com.pm.doctorservice.repository.DoctorRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class DoctorServiceImpl implements DoctorService {

    private final DoctorRepository doctorRepository;
    private final AvailabilityRepository availabilityRepository;


    public DoctorServiceImpl(DoctorRepository doctorRepository, AvailabilityRepository availabilityRepository) {
        this.doctorRepository = doctorRepository;
        this.availabilityRepository = availabilityRepository;
    }

    @Override
    public DoctorResponseDTO createDoctor(DoctorRequestDTO dto) {
        Doctor doctor = DoctorMapper.toEntity(dto);
        doctor = doctorRepository.save(doctor);
        return DoctorMapper.toDTO(doctor);
    }

    @Override
    public DoctorResponseDTO getDoctorById(UUID id) {
        Doctor doctor = doctorRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Doctor not found"));
        return DoctorMapper.toDTO(doctor);
    }

    @Override
    public List<DoctorResponseDTO> getAllDoctors() {
        return doctorRepository.findAll().stream()
                .map(DoctorMapper::toDTO)
                .collect(Collectors.toList());
    }

    @Override
    public DoctorResponseDTO updateDoctor(UUID id, DoctorRequestDTO dto) {
        Doctor doctor = doctorRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Doctor not found"));
        DoctorMapper.updateEntity(doctor, dto);
        doctor = doctorRepository.save(doctor);
        return DoctorMapper.toDTO(doctor);
    }

    @Override
    public void deleteDoctor(UUID id) {
        if (!doctorRepository.existsById(id)) {
            throw new EntityNotFoundException("Doctor not found");
        }
        doctorRepository.deleteById(id);
    }

    @Override
    public List<DoctorResponseDTO> getDoctorsBySpecialization(String specialization) {
        return doctorRepository.findBySpecialization(specialization).stream()
                .map(DoctorMapper::toDTO)
                .collect(Collectors.toList());
    }

    @Override
    public List<AvailabilityResponseDto> getDoctorAvailability(UUID doctorId) {
        if (!doctorRepository.existsById(doctorId)) {
            throw new EntityNotFoundException("Doctor not found");
        }
        return availabilityRepository.findByDoctorId(doctorId)
                .stream()
                .map(AvailabilityMapper::toDto)
                .toList();
    }

    @Override
    public AvailabilityResponseDto addAvailability(UUID doctorId, AvailabilityRequestDto dto) {
        Doctor doctor = doctorRepository.findById(doctorId)
                .orElseThrow(() -> new EntityNotFoundException("Doctor not found"));

        Availability availability = AvailabilityMapper.toEntity(dto);
        availability.setDoctor(doctor); // important

        Availability saved = availabilityRepository.save(availability);
        return AvailabilityMapper.toDto(saved);
    }

    @Override
    public void deleteAvailability(UUID doctorId, UUID availabilityId) {
        Availability availability = availabilityRepository.findById(availabilityId)
                .orElseThrow(() -> new EntityNotFoundException("Availability not found"));

        if (!availability.getDoctor().getId().equals(doctorId)) {
            throw new IllegalArgumentException("Availability does not belong to this doctor");
        }

        availabilityRepository.delete(availability);
    }

}


