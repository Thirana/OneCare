package com.pm.appointmentservice.mapper;

import com.pm.appointmentservice.dto.AppointmentRequestDTO;
import com.pm.appointmentservice.dto.AppointmentResponseDTO;
import com.pm.appointmentservice.model.Appointment;

public class AppointmentMapper {

    public static Appointment toEntity(AppointmentRequestDTO dto) {
        Appointment appointment = new Appointment();
        appointment.setPatientId(dto.patientId);
        appointment.setDoctorId(dto.doctorId);
        appointment.setAvailabilityId(dto.availabilityId);
        appointment.setNotes(dto.notes);
        return appointment;
    }

    public static AppointmentResponseDTO toDTO(Appointment appointment) {
        AppointmentResponseDTO dto = new AppointmentResponseDTO();
        dto.id = appointment.getId();
        dto.patientId = appointment.getPatientId();
        dto.doctorId = appointment.getDoctorId();
        dto.availabilityId = appointment.getAvailabilityId();
        dto.notes = appointment.getNotes();
        return dto;
    }

    public static void updateEntity(Appointment appointment, AppointmentRequestDTO dto) {
        appointment.setPatientId(dto.patientId);
        appointment.setDoctorId(dto.doctorId);
        appointment.setAvailabilityId(dto.availabilityId);
        appointment.setNotes(dto.notes);
    }
}