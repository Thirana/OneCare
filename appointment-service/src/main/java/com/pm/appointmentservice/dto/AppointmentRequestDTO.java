package com.pm.appointmentservice.dto;

import com.pm.appointmentservice.model.AppointmentStatus;
import jakarta.validation.constraints.NotNull;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.UUID;

public class AppointmentRequestDTO {
    @NotNull(message = "Patient ID is required")
    public UUID patientId;

    @NotNull(message = "Doctor ID is required")
    public UUID doctorId;

    @NotNull(message = "Date is required")
    public LocalDate date;

    @NotNull(message = "Start time is required")
    public LocalTime startTime;

    @NotNull(message = "End time is required")
    public LocalTime endTime;

    public String notes;

    @NotNull(message = "Status is required")
    public AppointmentStatus status;
}