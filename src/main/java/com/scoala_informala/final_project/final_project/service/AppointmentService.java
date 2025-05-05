package com.scoala_informala.final_project.final_project.service;

import com.scoala_informala.final_project.final_project.model.Appointment;
import com.scoala_informala.final_project.final_project.model.Doctor;
import com.scoala_informala.final_project.final_project.model.Patient;
import com.scoala_informala.final_project.final_project.model.Specialty;
import com.scoala_informala.final_project.final_project.repository.AppointmentRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;
import java.util.stream.Stream;

import static java.util.stream.Collectors.collectingAndThen;
import static java.util.stream.Collectors.counting;
import static java.util.stream.Collectors.groupingBy;

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

    public Doctor getMostBookedDoctor() {
        final List<Appointment> appointments = appointmentRepository.findAll();
        final Map<Doctor, Integer> doctorAppointmentCount = appointments.stream()
                .collect(groupingBy(Appointment::getDoctor, collectingAndThen(counting(), Long::intValue)));

        return doctorAppointmentCount.entrySet().stream()
                .max(Map.Entry.comparingByValue())
                .map(Map.Entry::getKey)
                .orElse(null);
    }

    public Specialty getMostBookedSpecialty() {
        final List<Appointment> appointments = appointmentRepository.findAll();
        final Map<Specialty, Integer> specialtyAppointmentCount = appointments.stream()
                .collect(groupingBy(appointment -> appointment.getDoctor().getSpecialty(), collectingAndThen(counting(), Long::intValue)));

        return specialtyAppointmentCount.entrySet().stream()
                .max(Map.Entry.comparingByValue())
                .map(Map.Entry::getKey)
                .orElse(null);
    }
}
