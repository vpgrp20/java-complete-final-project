package com.scoala_informala.final_project.final_project.model.dto;

import jakarta.validation.constraints.Pattern;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PatientUpdateDto {

    private int id;
    @Pattern(regexp = "^07\\d{8}$")
    private String phoneNumber;
}
