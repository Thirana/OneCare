package com.pm.doctorservice.mapper;

import com.pm.doctorservice.dto.AvailabilityRequestDto;
import com.pm.doctorservice.dto.AvailabilityResponseDto;
import com.pm.doctorservice.model.Availability;

public class AvailabilityMapper {
    public static Availability toEntity(AvailabilityRequestDto dto) {
        Availability availability = new Availability();
        availability.setDate(dto.date);
        availability.setStartTime(dto.startTime);
        availability.setEndTime(dto.endTime);
        availability.setBooked(dto.booked);
        return availability;
    }

    public static AvailabilityResponseDto toDto(Availability availability) {
        AvailabilityResponseDto dto = new AvailabilityResponseDto();
        dto.id = availability.getId();
        dto.date = availability.getDate();
        dto.startTime = availability.getStartTime();
        dto.endTime = availability.getEndTime();
        dto.booked = availability.isBooked();
        return dto;
    }
}
