package com.scoala_informala.final_project.final_project.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Table;

@Entity
@Table(name = "specializations")
public class SpecialtyProfile {

    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private String name;
}
