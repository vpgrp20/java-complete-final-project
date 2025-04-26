package com.scoala_informala.final_project.final_project.service;

import com.scoala_informala.final_project.final_project.model.SpecialtyProfile;
import com.scoala_informala.final_project.final_project.repository.SpecialtyProfileRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class SpecialtyProfileService {

    private final SpecialtyProfileRepository specialtyProfileRepository;

    public List<SpecialtyProfile> getAllSpecializations() {
        return specialtyProfileRepository.findAll();
    }
}
