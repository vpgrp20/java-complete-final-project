package com.scoala_informala.final_project.final_project.model;

public enum Specializare {

    GENERALA("Medicina Generala"), GASTROENTEROLOGIE("Gastroenterologie"), NEUROLOGIE("Neurologie"), STOMATOLOGIE("Stomatologie");

    private final String specializare;

    Specializare(String specializare) {
        this.specializare = specializare;
    }
}
