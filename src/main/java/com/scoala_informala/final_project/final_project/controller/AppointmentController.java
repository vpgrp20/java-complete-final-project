package com.scoala_informala.final_project.final_project.controller;

import com.scoala_informala.final_project.final_project.model.Doctor;
import com.scoala_informala.final_project.final_project.model.Patient;
import com.scoala_informala.final_project.final_project.model.Specialty;
import com.scoala_informala.final_project.final_project.model.User;
import com.scoala_informala.final_project.final_project.service.AppointmentService;
import com.scoala_informala.final_project.final_project.service.DoctorService;
import com.scoala_informala.final_project.final_project.service.SpecialtyService;
import com.scoala_informala.final_project.final_project.service.UserService;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.time.LocalDate;
import java.util.List;

@Controller
@RequiredArgsConstructor
@RequestMapping("/appointment")
public class AppointmentController {

    private final AppointmentService appointmentService;
    private final SpecialtyService specialtyService;
    private final DoctorService doctorService;
    private final UserService userService;

    @GetMapping("/specialties")
    public String showSpecialties(Model model) {
        model.addAttribute("specialtyList", specialtyService.getAllSpecialties());
        return "specialty-list";
    }

    @GetMapping("/doctors/{specialtyId}")
    public String showDoctorsBySpecialty(@PathVariable int specialtyId, Model model) {
        Specialty specialty = specialtyService.getSpecialtyById(specialtyId);
        List<Doctor> doctors = doctorService.getDoctorsBySpecialty(specialty);
        model.addAttribute("doctors", doctors);
        model.addAttribute("specialtyId", specialtyId);
        return "doctor-list";
    }

    @GetMapping("/dates/{doctorId}")
    public String showAvailableDates(@PathVariable int doctorId, Model model) {
        Doctor doctor = doctorService.getDoctorById(doctorId);
        model.addAttribute("doctor", doctor);
        model.addAttribute("availableDates", appointmentService.getAppointmentDates());
        return "appointment-dates";
    }


    @PostMapping("/book")
    public String bookAppointment(@RequestParam("doctorId") int doctorId, @RequestParam(value = "selectedDate", required = false) LocalDate selectedDate, RedirectAttributes redirectAttributes, HttpServletRequest httpServletRequest) {

        if (selectedDate == null) {
            redirectAttributes.addFlashAttribute("errorMessage", "Please select a date for your appointment.");
            return "redirect:/appointment/dates/" + doctorId;
        }
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = authentication.getName();
        User user = userService.findByUsername(username);
        Patient patient = userService.findPatientByUser(user);
        Doctor doctor = doctorService.getDoctorById(doctorId);

        appointmentService.makeAppointment(patient, doctor, selectedDate);
        return "appointment-confirmation";
    }
}
