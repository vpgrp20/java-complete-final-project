package com.scoala_informala.final_project.final_project.repository;

import com.scoala_informala.final_project.final_project.model.Specialty;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SpecialtyRepository extends JpaRepository<Specialty, Integer> {

    Specialty findBySpecialtyName(String name);

}
