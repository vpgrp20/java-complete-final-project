package com.scoala_informala.final_project.final_project.model.dto;

import com.scoala_informala.final_project.final_project.model.Patient;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PatientDto {

    private int id;
    private String name;
    private String phoneNumber;

    public static PatientDto mapToDto(Patient patient) {
        PatientDto patientDto = new PatientDto();
        patientDto.setId(patient.getId());
        patientDto.setName(patient.getName());
        patientDto.setPhoneNumber(patient.getPhoneNumber());
        return patientDto;
    }
}
