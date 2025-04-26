package com.scoala_informala.final_project.final_project.service;


import com.scoala_informala.final_project.final_project.model.Doctor;
import com.scoala_informala.final_project.final_project.repository.DoctorRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class DoctorService {

    private final DoctorRepository doctorRepository;

    public List<Doctor> getAllDoctors() {
        return doctorRepository.findAll();
    }
}
