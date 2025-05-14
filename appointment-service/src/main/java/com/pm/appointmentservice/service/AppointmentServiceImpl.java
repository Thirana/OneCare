package com.pm.appointmentservice.service;

import com.pm.appointmentservice.dto.AppointmentRequestDTO;
import com.pm.appointmentservice.dto.AppointmentResponseDTO;
import com.pm.appointmentservice.mapper.AppointmentMapper;
import com.pm.appointmentservice.model.Appointment;
import com.pm.appointmentservice.model.AppointmentStatus;
import com.pm.appointmentservice.repository.AppointmentRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.reactive.function.client.WebClient;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class AppointmentServiceImpl implements AppointmentService {

    private static final LocalTime BUSINESS_HOURS_START = LocalTime.of(9, 0);
    private static final LocalTime BUSINESS_HOURS_END = LocalTime.of(22, 0);

    private final AppointmentRepository appointmentRepository;
    private final WebClient.Builder webClientBuilder;

    public AppointmentServiceImpl(AppointmentRepository appointmentRepository, WebClient.Builder webClientBuilder) {
        this.appointmentRepository = appointmentRepository;
        this.webClientBuilder = webClientBuilder;
    }

    @Override
    @Transactional
    public AppointmentResponseDTO createAppointment(AppointmentRequestDTO dto) {
        validateTimeSlot(dto.date, dto.startTime, dto.endTime);

        // Verify patient exists
        verifyPatientExists(dto.patientId);

        // Verify doctor exists and is available
        verifyDoctorAvailability(dto.doctorId, dto.date, dto.startTime, dto.endTime);

        Appointment appointment = AppointmentMapper.toEntity(dto);
        appointment = appointmentRepository.save(appointment);

        try {
            // Update doctor availability to booked
            updateDoctorAvailability(dto.doctorId, dto.date, dto.startTime, dto.endTime);
        } catch (Exception e) {
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
    public AppointmentResponseDTO updateAppointment(UUID id, AppointmentRequestDTO dto) {
        validateTimeSlot(dto.date, dto.startTime, dto.endTime);

        Appointment appointment = appointmentRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Appointment not found"));

        // Check if doctor or date/time changed, and verify availability if so
        if (!appointment.getDoctorId().equals(dto.doctorId) ||
                !appointment.getDate().equals(dto.date) ||
                !appointment.getStartTime().equals(dto.startTime) ||
                !appointment.getEndTime().equals(dto.endTime)) {

            try {
                verifyDoctorAvailability(dto.doctorId, dto.date, dto.startTime, dto.endTime);

                // Release old slot
                releaseDoctorAvailability(appointment.getDoctorId(), appointment.getDate(),
                        appointment.getStartTime(), appointment.getEndTime());

                // Book new slot
                updateDoctorAvailability(dto.doctorId, dto.date, dto.startTime, dto.endTime);
            } catch (Exception e) {
                throw new RuntimeException("Failed to update doctor availability: " + e.getMessage());
            }
        }

        AppointmentMapper.updateEntity(appointment, dto);
        appointment = appointmentRepository.save(appointment);
        return AppointmentMapper.toDTO(appointment);
    }

    @Override
    @Transactional
    public void deleteAppointment(UUID id) {
        Appointment appointment = appointmentRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Appointment not found"));

        try {
            // Release doctor availability
            releaseDoctorAvailability(appointment.getDoctorId(), appointment.getDate(),
                    appointment.getStartTime(), appointment.getEndTime());

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

    @Override
    public List<AppointmentResponseDTO> getAppointmentsByDoctor(UUID doctorId) {
        return appointmentRepository.findByDoctorId(doctorId).stream()
                .map(AppointmentMapper::toDTO)
                .collect(Collectors.toList());
    }

    @Override
    public List<AppointmentResponseDTO> getAppointmentsByDate(LocalDate date) {
        return appointmentRepository.findByDate(date).stream()
                .map(AppointmentMapper::toDTO)
                .collect(Collectors.toList());
    }

    @Override
    public AppointmentResponseDTO updateAppointmentStatus(UUID id, AppointmentStatus status) {
        Appointment appointment = appointmentRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Appointment not found"));

        appointment.setStatus(status);

        // If appointment was cancelled, release the doctor's availability
        if (status == AppointmentStatus.CANCELLED) {
            releaseDoctorAvailability(appointment.getDoctorId(), appointment.getDate(),
                    appointment.getStartTime(), appointment.getEndTime());
        }

        appointment = appointmentRepository.save(appointment);
        return AppointmentMapper.toDTO(appointment);
    }

    private void validateTimeSlot(LocalDate date, LocalTime startTime, LocalTime endTime) {
        if (date == null || startTime == null || endTime == null) {
            throw new IllegalArgumentException("Date and time slots cannot be null");
        }

        if (date.isBefore(LocalDate.now())) {
            throw new IllegalArgumentException("Cannot book appointments in the past");
        }

        if (!startTime.isBefore(endTime)) {
            throw new IllegalArgumentException("End time must be after start time");
        }

        if (startTime.isBefore(BUSINESS_HOURS_START) || endTime.isAfter(BUSINESS_HOURS_END)) {
            throw new IllegalArgumentException(
                    String.format("Appointments must be between %s and %s",
                            BUSINESS_HOURS_START, BUSINESS_HOURS_END));
        }
    }

    // Helper methods for service communication
    private void verifyPatientExists(UUID patientId) {
        // This would be an actual REST call to the patient service in a real implementation
        // For now just check if the UUID is valid (not null)
        if (patientId == null) {
            throw new IllegalArgumentException("Patient ID cannot be null");
        }
    }

    private void verifyDoctorAvailability(UUID doctorId, LocalDate date, LocalTime startTime, LocalTime endTime) {
        // This would be an actual REST call to the doctor service in a real implementation
        // For now just check if the UUID is valid (not null)
        if (doctorId == null) {
            throw new IllegalArgumentException("Doctor ID cannot be null");
        }
    }

    private void updateDoctorAvailability(UUID doctorId, LocalDate date, LocalTime startTime, LocalTime endTime) {
        // This would update the doctor's availability in the doctor service
        // In a real implementation, this would call the doctor service via REST
    }

    private void releaseDoctorAvailability(UUID doctorId, LocalDate date, LocalTime startTime, LocalTime endTime) {
        // This would release the doctor's availability in the doctor service
        // In a real implementation, this would call the doctor service via REST
    }
}