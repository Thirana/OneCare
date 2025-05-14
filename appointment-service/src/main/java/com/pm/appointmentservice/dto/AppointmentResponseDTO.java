package com.pm.appointmentservice.dto;

import com.pm.appointmentservice.model.AppointmentStatus;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.UUID;

public class AppointmentResponseDTO {
    public UUID id;
    public UUID patientId;
    public UUID doctorId;
    public LocalDate date;
    public LocalTime startTime;
    public LocalTime endTime;
    public String notes;
    public AppointmentStatus status;
}