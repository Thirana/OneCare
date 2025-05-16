package com.pm.appointmentservice.service;

import com.pm.appointmentservice.dto.AppointmentRequestDTO;
import com.pm.appointmentservice.dto.AppointmentResponseDTO;

import java.util.List;
import java.util.UUID;

public interface AppointmentService {
    AppointmentResponseDTO createAppointment(AppointmentRequestDTO dto);

    AppointmentResponseDTO getAppointmentById(UUID id);

    List<AppointmentResponseDTO> getAllAppointments(int page, int size);

    void deleteAppointment(UUID id);

    List<AppointmentResponseDTO> getAppointmentsByPatient(UUID patientId);
}