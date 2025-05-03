package com.scoala_informala.final_project.final_project.service;

import com.scoala_informala.final_project.final_project.repository.AppointmentRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AppointmentService {

    private final AppointmentRepository appointmentRepository;
}
