package com.scoala_informala.final_project.final_project.controller;

import com.scoala_informala.final_project.final_project.model.dto.PatientUpdateDto;
import com.scoala_informala.final_project.final_project.service.PatientService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequiredArgsConstructor
@RequestMapping("/patient")
public class PatientController {

    private final PatientService patientService;

    @GetMapping("/all")
    public String getPatientList(Model model) {
        model.addAttribute("patients", patientService.getAllPatients());
        return "patientList.html";
    }

    @GetMapping("/update/{id}")
    public String getUpdatePatientForm(@PathVariable int id, Model model) {
        model.addAttribute("patient", patientService.getPatientById(id));
        return "updatePatientForm.html";
    }

    @PostMapping("/update")
    public String updatePatient(@ModelAttribute @Valid PatientUpdateDto patient, BindingResult bindingResult, Model model) {
        if (bindingResult.hasErrors()) {
            model.addAttribute("patient", patient);
            return "updatePatientForm.html";
        }
        patientService.updatePatient(patient);
        return "redirect:/patient/all";
    }

    @PostMapping("/delete/{id}")
    public String deletePatient(@PathVariable int id) {
        patientService.deletePatient(id);
        return "redirect:/patient/all";
    }
}
