package com.pm.doctorservice.dto;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.UUID;


public class AvailabilityResponseDto {
    public UUID id;
    public LocalDate date;
    public LocalTime startTime;
    public LocalTime endTime;
    public boolean booked;
}
