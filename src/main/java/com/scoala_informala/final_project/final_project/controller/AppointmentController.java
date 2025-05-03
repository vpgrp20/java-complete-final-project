package com.scoala_informala.final_project.final_project.controller;

import com.scoala_informala.final_project.final_project.service.AppointmentService;
import com.scoala_informala.final_project.final_project.service.DoctorService;
import com.scoala_informala.final_project.final_project.service.SpecialtyService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequiredArgsConstructor
@RequestMapping("/appointment")
public class AppointmentController {

    private final AppointmentService appointmentService;
    private final SpecialtyService specialtyService;
    private final DoctorService doctorService;
}
