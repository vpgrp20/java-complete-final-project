package com.scoala_informala.final_project.final_project.repository;

import com.scoala_informala.final_project.final_project.model.Doctor;
import com.scoala_informala.final_project.final_project.model.Specialty;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface DoctorRepository extends JpaRepository<Doctor, Integer> {

    Doctor findDoctorByName(String name);

    List<Doctor> findBySpecialty(Specialty specialty);

}
