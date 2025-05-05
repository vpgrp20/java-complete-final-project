package com.scoala_informala.final_project.final_project.controller;

import com.scoala_informala.final_project.final_project.model.CovidInfo;
import com.scoala_informala.final_project.final_project.model.Doctor;
import com.scoala_informala.final_project.final_project.model.Specialty;
import com.scoala_informala.final_project.final_project.service.AppointmentService;
import com.scoala_informala.final_project.final_project.service.CovidInfoService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequiredArgsConstructor
@RequestMapping("/info")
public class InformationController {

    private final CovidInfoService covidInfoService;
    private final AppointmentService appointmentService;

    @GetMapping
    public String getInfo(Model model) {

        CovidInfo covidInfo = covidInfoService.getCovidInfoForRomania();
        Doctor mostBookedDoctor = appointmentService.getMostBookedDoctor();
        Specialty mostBookedSpecialty = appointmentService.getMostBookedSpecialty();

        if (covidInfo != null) {
            model.addAttribute("totalCases", covidInfo.getTotalCases());
            model.addAttribute("lastUpdate", covidInfo.getLastUpdate());
            model.addAttribute("mostBookedDoctor", mostBookedDoctor);
            model.addAttribute("mostBookedSpecialty", mostBookedSpecialty);
        } else {
            model.addAttribute("errorMessage", "Failed to retrieve COVID information.");
        }

        return "info.html";
    }
}
