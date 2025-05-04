package com.scoala_informala.final_project.final_project.service;

import com.scoala_informala.final_project.final_project.model.Appointment;
import com.scoala_informala.final_project.final_project.model.Doctor;
import com.scoala_informala.final_project.final_project.model.Patient;
import com.scoala_informala.final_project.final_project.repository.AppointmentRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Stream;

@Service
@RequiredArgsConstructor
public class AppointmentService {

    private final AppointmentRepository appointmentRepository;

    public void makeAppointment(Patient patient, Doctor doctor, LocalDate date) {
        Appointment appointment = new Appointment();
        appointment.setPatient(patient);
        appointment.setDoctor(doctor);
        appointment.setDate(date);
        appointmentRepository.save(appointment);
    }

    public List<LocalDate> getAppointmentDates() {
        LocalDate tomorrow = LocalDate.now().plusDays(1);
        return Stream.iterate(tomorrow, day -> day.plusDays(1))
                .limit(14)
                .toList();
    }
}
