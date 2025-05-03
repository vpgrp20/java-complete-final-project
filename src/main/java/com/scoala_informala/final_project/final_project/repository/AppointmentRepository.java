package com.scoala_informala.final_project.final_project.repository;

import com.scoala_informala.final_project.final_project.model.Appointment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AppointmentRepository extends JpaRepository<Appointment, Integer> {

}
