package com.pm.appointmentservice.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;

import java.util.UUID;

@Entity
public class Appointment {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID id;

    @NotNull
    private UUID patientId;

    @NotNull
    private UUID doctorId;

    @NotNull
    private UUID availabilityId;

    private String notes;

    // Getters and Setters
    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public UUID getPatientId() {
        return patientId;
    }

    public void setPatientId(UUID patientId) {
        this.patientId = patientId;
    }

    public UUID getDoctorId() {
        return doctorId;
    }

    public void setDoctorId(UUID doctorId) {
        this.doctorId = doctorId;
    }

    public UUID getAvailabilityId() {
        return availabilityId;
    }

    public void setAvailabilityId(UUID availabilityId) {
        this.availabilityId = availabilityId;
    }

    public String getNotes() {
        return notes;
    }

    public void setNotes(String notes) {
        this.notes = notes;
    }
}