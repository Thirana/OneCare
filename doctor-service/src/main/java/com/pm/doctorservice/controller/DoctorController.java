package com.pm.doctorservice.controller;


import com.pm.doctorservice.dto.AvailabilityRequestDto;
import com.pm.doctorservice.dto.AvailabilityResponseDto;
import com.pm.doctorservice.dto.DoctorRequestDTO;
import com.pm.doctorservice.dto.DoctorResponseDTO;
import com.pm.doctorservice.model.Availability;
import com.pm.doctorservice.service.DoctorService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/doctors")
public class DoctorController {


    private final DoctorService doctorService;

    public DoctorController(DoctorService doctorService) {
        this.doctorService = doctorService;
    }


    @PostMapping
    public ResponseEntity<DoctorResponseDTO> addDoctor(@Valid @RequestBody DoctorRequestDTO dto) {
        return new ResponseEntity<>(doctorService.createDoctor(dto), HttpStatus.CREATED);
    }


    @GetMapping("/{id}")
    public ResponseEntity<DoctorResponseDTO> getDoctorById(@PathVariable UUID id) {
        return ResponseEntity.ok(doctorService.getDoctorById(id));
    }

    @GetMapping
    public ResponseEntity<List<DoctorResponseDTO>> getAllDoctors() {
        return ResponseEntity.ok(doctorService.getAllDoctors());
    }

    @PutMapping("/{id}")
    public ResponseEntity<DoctorResponseDTO> updateDoctor(
            @PathVariable UUID id,
            @Valid @RequestBody DoctorRequestDTO dto
    ) {
        return ResponseEntity.ok(doctorService.updateDoctor(id, dto));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteDoctor(@PathVariable UUID id) {
        doctorService.deleteDoctor(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/specialization/{spec}")
    public ResponseEntity<List<DoctorResponseDTO>> getDoctorsBySpecialization(@PathVariable String spec) {
        return ResponseEntity.ok(doctorService.getDoctorsBySpecialization(spec));
    }

    @GetMapping("/{id}/availability")
    public ResponseEntity<List<AvailabilityResponseDto>> getAvailability(@PathVariable UUID id) {
        return ResponseEntity.ok(doctorService.getDoctorAvailability(id));
    }

    @PostMapping("/{id}/availability")
    public ResponseEntity<AvailabilityResponseDto> addAvailability(
            @PathVariable UUID id,
            @RequestBody AvailabilityRequestDto requestDto) {
        return ResponseEntity.status(HttpStatus.CREATED).body(
                doctorService.addAvailability(id, requestDto)
        );
    }

    @PutMapping("/{doctorId}/availabilities/{availabilityId}")
    public ResponseEntity<AvailabilityResponseDto> updateAvailability(
            @PathVariable UUID doctorId,
            @PathVariable UUID availabilityId,
            @Valid @RequestBody AvailabilityRequestDto requestDto) {
        return ResponseEntity.ok(doctorService.updateAvailability(doctorId, availabilityId, requestDto));
    }

    @DeleteMapping("/{doctorId}/availabilities/{availabilityId}")
    public ResponseEntity<Void> deleteAvailability(
            @PathVariable UUID doctorId,
            @PathVariable UUID availabilityId) {
        doctorService.deleteAvailability(doctorId, availabilityId);
        return ResponseEntity.noContent().build();
    }

}
