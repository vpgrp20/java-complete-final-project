package com.scoala_informala.final_project.final_project.service;

import com.scoala_informala.final_project.final_project.model.Specialty;
import com.scoala_informala.final_project.final_project.repository.SpecialtyRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class SpecialtyService {

    private final SpecialtyRepository specialtyRepository;

    public List<Specialty> getAllSpecialties() {
        return specialtyRepository.findAll();
    }

    public void addSpecialty(Specialty specialty) {
        if (specialtyRepository.findBySpecialtyName(specialty.getSpecialtyName()) == null) {
            specialtyRepository.save(specialty);
        }
    }

    public Specialty getSpecialtyById(int id) {
        return specialtyRepository.findById(id).orElseThrow(() -> new RuntimeException("Specialty Not Found"));
    }
}
