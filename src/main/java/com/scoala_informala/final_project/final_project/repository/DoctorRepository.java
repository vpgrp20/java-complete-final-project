package com.scoala_informala.final_project.final_project.repository;

import com.scoala_informala.final_project.final_project.model.Doctor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DoctorRepository extends JpaRepository<Doctor, Integer> {

}
