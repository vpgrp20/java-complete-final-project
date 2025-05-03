package com.scoala_informala.final_project.final_project.controller;

import com.scoala_informala.final_project.final_project.model.CovidInfo;
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

    @GetMapping("/covid")
    public String getCovidInfo(Model model) {

        CovidInfo covidInfo = covidInfoService.getCovidInfoForRomania();

        if (covidInfo != null) {
            model.addAttribute("totalCases", covidInfo.getTotalCases());
            model.addAttribute("lastUpdate", covidInfo.getLastUpdate());
        } else {
            model.addAttribute("errorMessage", "Failed to retrieve COVID information.");
        }

        return "covidInfo.html";
    }
}
