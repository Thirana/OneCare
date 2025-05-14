package com.pm.appointmentservice.mapper;

import com.pm.appointmentservice.dto.AppointmentRequestDTO;
import com.pm.appointmentservice.dto.AppointmentResponseDTO;
import com.pm.appointmentservice.model.Appointment;

public class AppointmentMapper {

    public static Appointment toEntity(AppointmentRequestDTO dto) {
        Appointment appointment = new Appointment();
        appointment.setPatientId(dto.patientId);
        appointment.setDoctorId(dto.doctorId);
        appointment.setDate(dto.date);
        appointment.setStartTime(dto.startTime);
        appointment.setEndTime(dto.endTime);
        appointment.setNotes(dto.notes);
        appointment.setStatus(dto.status);
        return appointment;
    }

    public static AppointmentResponseDTO toDTO(Appointment appointment) {
        AppointmentResponseDTO dto = new AppointmentResponseDTO();
        dto.id = appointment.getId();
        dto.patientId = appointment.getPatientId();
        dto.doctorId = appointment.getDoctorId();
        dto.date = appointment.getDate();
        dto.startTime = appointment.getStartTime();
        dto.endTime = appointment.getEndTime();
        dto.notes = appointment.getNotes();
        dto.status = appointment.getStatus();
        return dto;
    }

    public static void updateEntity(Appointment appointment, AppointmentRequestDTO dto) {
        appointment.setPatientId(dto.patientId);
        appointment.setDoctorId(dto.doctorId);
        appointment.setDate(dto.date);
        appointment.setStartTime(dto.startTime);
        appointment.setEndTime(dto.endTime);
        appointment.setNotes(dto.notes);
        appointment.setStatus(dto.status);
    }
}