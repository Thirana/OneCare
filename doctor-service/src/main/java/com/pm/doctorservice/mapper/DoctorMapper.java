package com.pm.doctorservice.mapper;

import com.pm.doctorservice.dto.DoctorRequestDTO;
import com.pm.doctorservice.dto.DoctorResponseDTO;
import com.pm.doctorservice.model.Doctor;

public class DoctorMapper {

    public static Doctor toEntity(DoctorRequestDTO dto) {
        Doctor doctor = new Doctor();
        doctor.setName(dto.name);
        doctor.setSpecialization(dto.specialization);
        doctor.setEmail(dto.email);
        doctor.setPhone(dto.phone);
        doctor.setAddress(dto.address);
        return doctor;
    }

    public static DoctorResponseDTO toDTO(Doctor doctor) {
        DoctorResponseDTO dto = new DoctorResponseDTO();
        dto.id = doctor.getId();
        dto.name = doctor.getName();
        dto.specialization = doctor.getSpecialization();
        dto.email = doctor.getEmail();
        dto.phone = doctor.getPhone();
        dto.address = doctor.getAddress();
        return dto;
    }


    public static void updateEntity(Doctor doctor, DoctorRequestDTO dto) {
        doctor.setName(dto.name);
        doctor.setSpecialization(dto.specialization);
        doctor.setEmail(dto.email);
        doctor.setPhone(dto.phone);
        doctor.setAddress(dto.address);
    }

}
