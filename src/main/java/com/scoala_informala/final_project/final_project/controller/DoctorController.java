package com.scoala_informala.final_project.final_project.controller;

import com.scoala_informala.final_project.final_project.model.Doctor;
import com.scoala_informala.final_project.final_project.service.DoctorService;
import com.scoala_informala.final_project.final_project.service.SpecialtyService;
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
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequiredArgsConstructor
@RequestMapping("/doctor")
public class DoctorController {

    private final DoctorService doctorService;
    private final SpecialtyService specialtyService;

    @GetMapping("/add")
    public String getAddDoctorForm(Model model) {
        model.addAttribute("doctor", new Doctor());
        model.addAttribute("specialtyList", specialtyService.getAllSpecialties());
        return "addDoctorForm.html";
    }

    @PostMapping
    public String addDoctor(@ModelAttribute @Valid Doctor doctor, BindingResult bindingResult, Model model) {
        if (bindingResult.hasErrors()) {
            model.addAttribute("specialtyList", specialtyService.getAllSpecialties());
            return "addDoctorForm.html";
        }
        doctorService.addDoctor(doctor);
        return "redirect:/";
    }

    @GetMapping("/all")
    public String getDoctorList(Model model) {
        model.addAttribute("doctors", doctorService.getAllDoctors());
        model.addAttribute("specialtyList", specialtyService.getAllSpecialties());
        return "doctorList.html";
    }

    @PostMapping("/delete")
    public String deleteDoctor(@RequestParam("id") Integer id) {
        doctorService.deleteDoctor(id);
        return "redirect:/doctor/all";
    }

    @GetMapping("update/{id}")
    public String getUpdateDoctorForm(@PathVariable Integer id, Model model) {
        model.addAttribute("doctor", doctorService.getDoctorById(id));
        return "updateDoctorForm.html";
    }

    @PostMapping("/update")
    public String updateDoctor(@ModelAttribute @Valid Doctor doctor, BindingResult bindingResult, Model model) {
        if (bindingResult.hasErrors()) {
            model.addAttribute("doctor", doctor);
            return "updateDoctorForm.html";
        }
        doctorService.updateDoctor(doctor);
        return "redirect:/doctor/all";
    }

}
