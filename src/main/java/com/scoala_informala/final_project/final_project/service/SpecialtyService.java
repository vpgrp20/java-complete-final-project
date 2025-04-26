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

    public List<Specialty> getAllSpecializations() {
        return specialtyRepository.findAll();
    }
}
