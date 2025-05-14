package com.pm.appointmentservice.service;

import com.pm.appointmentservice.dto.AppointmentRequestDTO;
import com.pm.appointmentservice.dto.AppointmentResponseDTO;
import com.pm.appointmentservice.model.AppointmentStatus;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

public interface AppointmentService {
    AppointmentResponseDTO createAppointment(AppointmentRequestDTO dto);

    AppointmentResponseDTO getAppointmentById(UUID id);

    List<AppointmentResponseDTO> getAllAppointments(int page, int size);

    AppointmentResponseDTO updateAppointment(UUID id, AppointmentRequestDTO dto);

    void deleteAppointment(UUID id);

    List<AppointmentResponseDTO> getAppointmentsByPatient(UUID patientId);

    List<AppointmentResponseDTO> getAppointmentsByDoctor(UUID doctorId);

    List<AppointmentResponseDTO> getAppointmentsByDate(LocalDate date);

    AppointmentResponseDTO updateAppointmentStatus(UUID id, AppointmentStatus status);
}