package com.scoala_informala.final_project.final_project.service;


import com.scoala_informala.final_project.final_project.model.Doctor;
import com.scoala_informala.final_project.final_project.repository.DoctorRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class DoctorService {

    private final DoctorRepository doctorRepository;

    public List<Doctor> getAllDoctors() {
        return doctorRepository.findAll();
    }

    public void addDoctor(Doctor doctor) {
        if (doctorRepository.findDoctorByName(doctor.getName()) == null) {
            doctorRepository.save(doctor);
        }
    }

    public void deleteDoctor(Integer id) {
        final Doctor doctor = doctorRepository.findById(id).orElseThrow(RuntimeException::new);
        doctorRepository.delete(doctor);
    }
}
