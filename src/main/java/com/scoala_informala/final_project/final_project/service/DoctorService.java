package com.scoala_informala.final_project.final_project.service;


import com.scoala_informala.final_project.final_project.model.Doctor;
import com.scoala_informala.final_project.final_project.model.Specialty;
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

    public void addDoctor(Doctor doctor) {
        if (doctorRepository.findDoctorByName(doctor.getName()) == null) {
            doctorRepository.save(doctor);
        }
    }

    public void deleteDoctor(Integer id) {
        final Doctor doctor = doctorRepository.findById(id).orElseThrow(RuntimeException::new);
        doctorRepository.delete(doctor);
    }

    public List<Doctor> getDoctorsBySpecialty(Specialty specialty) {
        return doctorRepository.findBySpecialty(specialty);
    }

    public Doctor getDoctorById(int id) {
        return doctorRepository.findById(id).orElseThrow(() -> new RuntimeException("Doctor not found"));
    }
}
