package com.scoala_informala.final_project.final_project.repository;

import com.scoala_informala.final_project.final_project.model.SpecialtyProfile;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SpecialtyProfileRepository extends JpaRepository<SpecialtyProfile, Integer> {

}
