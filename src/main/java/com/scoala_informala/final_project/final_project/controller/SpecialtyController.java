package com.scoala_informala.final_project.final_project.controller;


import com.scoala_informala.final_project.final_project.model.Specialty;
import com.scoala_informala.final_project.final_project.service.SpecialtyService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;


@Controller
@RequestMapping("/specialty")
@RequiredArgsConstructor
public class SpecialtyController {

    private final SpecialtyService specialtyService;

    @GetMapping("/add")
    public String getAddSpecialtyForm(Model model) {
        model.addAttribute("specialty", new Specialty());
        return "addSpecialtyForm.html";
    }

    @PostMapping
    public String addSpecialty(@ModelAttribute Specialty specialty) {
        specialtyService.addSpecialty(specialty);
        return "redirect:/specialty/all";
    }

    @GetMapping("/all")
    public String getAllSpecialties(Model model) {
        model.addAttribute("specialtyList", specialtyService.getAllSpecialties());
        return "specialtyList.html";
    }
}
