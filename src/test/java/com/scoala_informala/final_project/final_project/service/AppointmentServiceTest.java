package com.scoala_informala.final_project.final_project.service;

import com.scoala_informala.final_project.final_project.model.Appointment;
import com.scoala_informala.final_project.final_project.model.Doctor;
import com.scoala_informala.final_project.final_project.model.Patient;
import com.scoala_informala.final_project.final_project.model.Specialty;
import com.scoala_informala.final_project.final_project.repository.AppointmentRepository;
import com.scoala_informala.final_project.final_project.repository.DoctorRepository;
import com.scoala_informala.final_project.final_project.repository.PatientRepository;
import com.scoala_informala.final_project.final_project.repository.SpecialtyRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

@DataJpaTest
@Import(AppointmentService.class)
@ActiveProfiles("test")
public class AppointmentServiceTest {

    private final AppointmentRepository appointmentRepository;
    private final AppointmentService appointmentService;
    private final DoctorRepository doctorRepository;
    private final PatientRepository patientRepository;
    private final SpecialtyRepository specialtyRepository;

    @Autowired
    public AppointmentServiceTest(final AppointmentRepository appointmentRepository, final AppointmentService appointmentService, final DoctorRepository doctorRepository, final PatientRepository patientRepository, final SpecialtyRepository specialtyRepository) {
        this.appointmentRepository = appointmentRepository;
        this.appointmentService = appointmentService;
        this.doctorRepository = doctorRepository;
        this.patientRepository = patientRepository;
        this.specialtyRepository = specialtyRepository;
    }

    private Doctor doctor1;
    private Doctor doctor2;
    private Specialty specialty1;
    private Specialty specialty2;
    private Patient patient1;
    private Patient patient2;

    @BeforeEach
    void setUp() {
        specialty1 = new Specialty();
        specialty1.setSpecialtyName("FIZIOKINETOTERAPIE");
        specialty2 = new Specialty();
        specialty2.setSpecialtyName("MEDICINA GENERALA");
        specialtyRepository.save(specialty1);
        specialtyRepository.save(specialty2);

        doctor1 = new Doctor();
        doctor1.setName("Doctor One");
        doctor1.setSpecialty(specialty1);
        doctorRepository.save(doctor1);

        doctor2 = new Doctor();
        doctor2.setName("Doctor Two");
        doctor2.setSpecialty(specialty2);
        doctorRepository.save(doctor2);

        patient1 = new Patient();
        patient1.setName("Patient One");
        patientRepository.save(patient1);
        patient2 = new Patient();
        patient2.setName("Patient Two");
        patientRepository.save(patient2);
    }

    @Test
    @Transactional
    void testMakeAppointment() {
        LocalDate date = LocalDate.now();

        appointmentService.makeAppointment(patient1, doctor1, date);

        List<Appointment> appointments = appointmentRepository.findAll();
        assertEquals(1, appointments.size());
        assertEquals(patient1, appointments.get(0).getPatient());
        assertEquals(doctor1, appointments.get(0).getDoctor());
        assertEquals(date, appointments.get(0).getDate());
    }

    @Test
    @Transactional
    void testGetAppointmentDates() {
        LocalDate today = LocalDate.now();
        LocalDate tomorrow = today.plusDays(1);
        List<LocalDate> expectedDates = new ArrayList<>();
        for (int i = 0; i < 14; i++) {
            expectedDates.add(tomorrow.plusDays(i));
        }

        List<LocalDate> actualDates = appointmentService.getAppointmentDates();

        assertEquals(expectedDates, actualDates);
    }

    @Test
    @Transactional
    void testGetMostBookedDoctor_noAppointments() {
        List<Appointment> appointments = appointmentRepository.findAll();
        assertEquals(0, appointments.size());
        Doctor mostBookedDoctor = appointmentService.getMostBookedDoctor();

        assertNull(mostBookedDoctor);
    }

    @Test
    @Transactional
    void testGetMostBookedDoctor_singleDoctor() {
        Appointment appointment1 = new Appointment();
        appointment1.setDoctor(doctor1);
        appointmentRepository.save(appointment1);
        Appointment appointment2 = new Appointment();
        appointment2.setDoctor(doctor1);
        appointmentRepository.save(appointment2);

        Doctor mostBookedDoctor = appointmentService.getMostBookedDoctor();

        assertEquals(doctor1, mostBookedDoctor);
    }

    @Test
    @Transactional
    void testGetMostBookedDoctor_multipleDoctors() {
        Appointment appointment1 = new Appointment();
        appointment1.setDoctor(doctor1);
        appointmentRepository.save(appointment1);
        Appointment appointment2 = new Appointment();
        appointment2.setDoctor(doctor1);
        appointmentRepository.save(appointment2);
        Appointment appointment3 = new Appointment();
        appointment3.setDoctor(doctor2);
        appointmentRepository.save(appointment3);
        Appointment appointment4 = new Appointment();
        appointment4.setDoctor(doctor2);
        appointmentRepository.save(appointment4);
        Appointment appointment5 = new Appointment();
        appointment5.setDoctor(doctor1);
        appointmentRepository.save(appointment5);

        Doctor mostBookedDoctor = appointmentService.getMostBookedDoctor();

        assertEquals(doctor1, mostBookedDoctor);
    }

    @Test
    @Transactional
    void testGetMostBookedSpecialty_noAppointments() {
        Specialty mostBookedSpecialty = appointmentService.getMostBookedSpecialty();

        assertNull(mostBookedSpecialty);
    }

    @Test
    @Transactional
    void testGetMostBookedSpecialty_singleSpecialty() {
        Doctor localDoctor1 = new Doctor();
        localDoctor1.setSpecialty(specialty1);
        doctorRepository.save(localDoctor1);
        Appointment appointment1 = new Appointment();
        appointment1.setDoctor(localDoctor1);
        appointmentRepository.save(appointment1);
        Appointment appointment2 = new Appointment();
        appointment2.setDoctor(localDoctor1);
        appointmentRepository.save(appointment2);

        Specialty mostBookedSpecialty = appointmentService.getMostBookedSpecialty();

        assertEquals(specialty1, mostBookedSpecialty);
    }

    @Test
    @Transactional
    void testGetMostBookedSpecialty_multipleSpecialties() {
        Doctor localDoctor1 = new Doctor();
        localDoctor1.setSpecialty(specialty1);
        doctorRepository.save(localDoctor1);
        Doctor localDoctor2 = new Doctor();
        localDoctor2.setSpecialty(specialty2);
        doctorRepository.save(localDoctor2);

        Appointment appointment1 = new Appointment();
        appointment1.setDoctor(localDoctor1);
        appointmentRepository.save(appointment1);
        Appointment appointment2 = new Appointment();
        appointment2.setDoctor(localDoctor2);
        appointmentRepository.save(appointment2);
        Appointment appointment3 = new Appointment();
        appointment3.setDoctor(localDoctor2);
        appointmentRepository.save(appointment3);
        Appointment appointment4 = new Appointment();
        appointment4.setDoctor(localDoctor2);
        appointmentRepository.save(appointment4);

        Specialty mostBookedSpecialty = appointmentService.getMostBookedSpecialty();

        assertEquals(specialty2, mostBookedSpecialty);
    }
}
