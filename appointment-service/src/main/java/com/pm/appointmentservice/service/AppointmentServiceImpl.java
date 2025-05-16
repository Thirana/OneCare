package com.pm.appointmentservice.service;

import com.pm.appointmentservice.dto.AppointmentRequestDTO;
import com.pm.appointmentservice.dto.AppointmentResponseDTO;
import com.pm.appointmentservice.mapper.AppointmentMapper;
import com.pm.appointmentservice.model.Appointment;
import com.pm.appointmentservice.repository.AppointmentRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.reactive.function.client.WebClient;

import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class AppointmentServiceImpl implements AppointmentService {
    private final AppointmentRepository appointmentRepository;
    private final WebClient.Builder webClientBuilder;

    public AppointmentServiceImpl(AppointmentRepository appointmentRepository, WebClient.Builder webClientBuilder) {
        this.appointmentRepository = appointmentRepository;
        this.webClientBuilder = webClientBuilder;
    }

    @Override
    @Transactional
    public AppointmentResponseDTO createAppointment(AppointmentRequestDTO dto) {
        // Verify patient exists
        verifyPatientExists(dto.patientId);

        // Verify availability exists and is not booked
        verifyAvailabilityNotBooked(dto.doctorId, dto.availabilityId);

        Appointment appointment = AppointmentMapper.toEntity(dto);
        appointment = appointmentRepository.save(appointment);

        try {
            // Update availability to booked
            updateAvailabilityStatus(dto.doctorId, dto.availabilityId, true);
        } catch (Exception e) {
            // Rollback the appointment creation if we can't update the availability
            appointmentRepository.delete(appointment);
            throw new RuntimeException("Failed to update doctor availability: " + e.getMessage());
        }

        return AppointmentMapper.toDTO(appointment);
    }

    @Override
    public AppointmentResponseDTO getAppointmentById(UUID id) {
        Appointment appointment = appointmentRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Appointment not found"));
        return AppointmentMapper.toDTO(appointment);
    }

    @Override
    public List<AppointmentResponseDTO> getAllAppointments(int page, int size) {
        Page<Appointment> appointments = appointmentRepository.findAll(PageRequest.of(page, size));
        return appointments.getContent().stream()
                .map(AppointmentMapper::toDTO)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional
    public void deleteAppointment(UUID id) {
        Appointment appointment = appointmentRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Appointment not found"));

        try {
            // Release the availability slot
            updateAvailabilityStatus(appointment.getDoctorId(), appointment.getAvailabilityId(), false);
            appointmentRepository.deleteById(id);
        } catch (Exception e) {
            throw new RuntimeException("Failed to release doctor availability: " + e.getMessage());
        }
    }

    @Override
    public List<AppointmentResponseDTO> getAppointmentsByPatient(UUID patientId) {
        return appointmentRepository.findByPatientId(patientId).stream()
                .map(AppointmentMapper::toDTO)
                .collect(Collectors.toList());
    }

    // Helper methods for service communication
    private void verifyPatientExists(UUID patientId) {
        if (patientId == null) {
            throw new IllegalArgumentException("Patient ID cannot be null");
        }
        
        // TODO: Add actual patient service verification when it's implemented
    }

    private void verifyAvailabilityNotBooked(UUID doctorId, UUID availabilityId) {
        if (doctorId == null || availabilityId == null) {
            throw new IllegalArgumentException("Doctor ID and Availability ID cannot be null");
        }

        List<Map<String, Object>> availabilities = webClientBuilder.build()
                .get()
                .uri("http://doctor-service/doctors/{doctorId}/availability", doctorId)
                .retrieve()
                .bodyToMono(new ParameterizedTypeReference<List<Map<String, Object>>>() {})
                .block();

        if (availabilities == null || availabilities.isEmpty()) {
            throw new EntityNotFoundException("No availabilities found for doctor");
        }

        Map<String, Object> availability = availabilities.stream()
                .filter(a -> availabilityId.equals(UUID.fromString((String) a.get("id"))))
                .findFirst()
                .orElseThrow(() -> new EntityNotFoundException("Availability slot not found"));

        Boolean isBooked = (Boolean) availability.get("booked");
        if (isBooked != null && isBooked) {
            throw new IllegalStateException("This availability slot is already booked");
        }
    }

    private void updateAvailabilityStatus(UUID doctorId, UUID availabilityId, boolean booked) {
        // First get the current availability to preserve all fields
        List<Map<String, Object>> availabilities = webClientBuilder.build()
                .get()
                .uri("http://doctor-service/doctors/{doctorId}/availability", doctorId)
                .retrieve()
                .bodyToMono(new ParameterizedTypeReference<List<Map<String, Object>>>() {})
                .block();

        if (availabilities == null || availabilities.isEmpty()) {
            throw new EntityNotFoundException("No availabilities found for doctor");
        }

        Map<String, Object> availability = availabilities.stream()
                .filter(a -> availabilityId.equals(UUID.fromString((String) a.get("id"))))
                .findFirst()
                .orElseThrow(() -> new EntityNotFoundException("Availability slot not found"));

        // Create update request preserving all fields except booked status
        Map<String, Object> updateRequest = Map.of(
            "date", availability.get("date"),
            "startTime", availability.get("startTime"),
            "endTime", availability.get("endTime"),
            "booked", booked
        );

        webClientBuilder.build()
                .put()
                .uri("http://doctor-service/doctors/{doctorId}/availabilities/{availabilityId}",
                        doctorId, availabilityId)
                .bodyValue(updateRequest)
                .retrieve()
                .bodyToMono(Void.class)
                .block();
    }
}