package com.pm.appointmentservice.dto;

import java.util.UUID;

public class AppointmentResponseDTO {
    public UUID id;
    public UUID patientId;
    public UUID doctorId;
    public UUID availabilityId;
    public String notes;
}