package com.pm.doctorservice.dto;

import java.time.LocalDate;
import java.time.LocalTime;

public class AvailabilityRequestDto {
    public LocalDate date;
    public LocalTime startTime;
    public LocalTime endTime;
    public boolean booked;
}
