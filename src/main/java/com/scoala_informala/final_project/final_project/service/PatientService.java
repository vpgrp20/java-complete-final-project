package com.scoala_informala.final_project.final_project.service;

import com.scoala_informala.final_project.final_project.model.Patient;
import com.scoala_informala.final_project.final_project.model.dto.PatientDto;
import com.scoala_informala.final_project.final_project.model.dto.PatientUpdateDto;
import com.scoala_informala.final_project.final_project.repository.PatientRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class PatientService {

    private final PatientRepository patientRepository;

    public List<PatientDto> getAllPatients() {
        final List<Patient> patients = patientRepository.findAll();
        return patients.stream()
                .map(PatientDto::mapToDto)
                .toList();
    }

    public PatientUpdateDto getPatientById(int id) {
        Patient patient = patientRepository.findById(id).orElseThrow(() -> new RuntimeException("Patient with id " + id + " not found."));
        PatientUpdateDto patientUpdateDto = new PatientUpdateDto();
        patientUpdateDto.setId(patient.getId());
        patientUpdateDto.setPhoneNumber(patient.getPhoneNumber());
        return patientUpdateDto;
    }

    @Transactional
    public void updatePatient(PatientUpdateDto patientDto) {
        int id = patientDto.getId();
        Patient patientToUpdate = patientRepository.findById(id).orElseThrow(() -> new RuntimeException("Patient with id " + id + " not found."));
        patientToUpdate.setPhoneNumber(patientDto.getPhoneNumber());
        patientRepository.save(patientToUpdate);
    }

    @Transactional
    public void deletePatient(int id) {
        Patient patient = patientRepository.findById(id).orElseThrow(() -> new RuntimeException("Patient with id " + id + " not found."));
        patientRepository.delete(patient);
    }
}
