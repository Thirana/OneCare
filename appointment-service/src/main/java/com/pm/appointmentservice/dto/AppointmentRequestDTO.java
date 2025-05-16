package com.pm.appointmentservice.dto;

import jakarta.validation.constraints.NotNull;

import java.util.UUID;

public class AppointmentRequestDTO {
    @NotNull(message = "Patient ID is required")
    public UUID patientId;

    @NotNull(message = "Doctor ID is required")
    public UUID doctorId;

    @NotNull(message = "Availability ID is required")
    public UUID availabilityId;

    public String notes;
}