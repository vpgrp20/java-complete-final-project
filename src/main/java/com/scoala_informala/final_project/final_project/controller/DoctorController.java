package com.scoala_informala.final_project.final_project.controller;

import com.scoala_informala.final_project.final_project.model.Doctor;
import com.scoala_informala.final_project.final_project.service.DoctorService;
import com.scoala_informala.final_project.final_project.service.SpecialtyService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequiredArgsConstructor
@RequestMapping("/doctor")
public class DoctorController {

    private final DoctorService doctorService;
    private final SpecialtyService specialtyService;

    @GetMapping("/add")
    public String getAddSpecialtyForm(Model model) {
        model.addAttribute("doctor", new Doctor());
        model.addAttribute("specialtyList", specialtyService.getAllSpecialties());
        return "addDoctorForm.html";
    }

    @PostMapping
    public String addSpecialty(@ModelAttribute @Valid Doctor doctor, BindingResult bindingResult, Model model) {
        if (bindingResult.hasErrors()) {
            model.addAttribute("specialtyList", specialtyService.getAllSpecialties());
            return "addDoctorForm.html";
        }
        doctorService.addDoctor(doctor);
        return "redirect:/";
    }

}
