package com.pm.appointmentservice.controller;

import com.pm.appointmentservice.dto.AppointmentRequestDTO;
import com.pm.appointmentservice.dto.AppointmentResponseDTO;
import com.pm.appointmentservice.service.AppointmentService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/appointments")
public class AppointmentController {

    private final AppointmentService appointmentService;

    public AppointmentController(AppointmentService appointmentService) {
        this.appointmentService = appointmentService;
    }

    @PostMapping
    public ResponseEntity<AppointmentResponseDTO> createAppointment(@Valid @RequestBody AppointmentRequestDTO requestDTO) {
        return new ResponseEntity<>(appointmentService.createAppointment(requestDTO), HttpStatus.CREATED);
    }

    @GetMapping("/{id}")
    public ResponseEntity<AppointmentResponseDTO> getAppointmentById(@PathVariable UUID id) {
        return ResponseEntity.ok(appointmentService.getAppointmentById(id));
    }

    @GetMapping
    public ResponseEntity<List<AppointmentResponseDTO>> getAllAppointments(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {
        return ResponseEntity.ok(appointmentService.getAllAppointments(page, size));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteAppointment(@PathVariable UUID id) {
        appointmentService.deleteAppointment(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/patient/{patientId}")
    public ResponseEntity<List<AppointmentResponseDTO>> getAppointmentsByPatient(@PathVariable UUID patientId) {
        return ResponseEntity.ok(appointmentService.getAppointmentsByPatient(patientId));
    }

}