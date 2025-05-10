package com.scoala_informala.final_project.final_project.service;

import com.scoala_informala.final_project.final_project.model.Doctor;
import com.scoala_informala.final_project.final_project.model.Specialty;
import com.scoala_informala.final_project.final_project.repository.DoctorRepository;
import com.scoala_informala.final_project.final_project.repository.SpecialtyRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;


@DataJpaTest
@Import(DoctorService.class)
@ActiveProfiles("test")
public class DoctorServiceTest {

    private final DoctorRepository doctorRepository;
    private final SpecialtyRepository specialtyRepository;
    private final DoctorService doctorService;

    @Autowired
    public DoctorServiceTest(final DoctorRepository doctorRepository, final SpecialtyRepository specialtyRepository, final DoctorService doctorService) {
        this.doctorRepository = doctorRepository;
        this.specialtyRepository = specialtyRepository;
        this.doctorService = doctorService;
    }

    private Specialty specialty1;
    private Specialty specialty2;

    @BeforeEach
    void setUp() {
        specialty1 = new Specialty();
        specialty1.setSpecialtyName("FIZIOKINETOTERAPIE");
        specialtyRepository.save(specialty1);

        specialty2 = new Specialty();
        specialty2.setSpecialtyName("MEDICINA GENERALA");
        specialtyRepository.save(specialty2);
    }

    @Test
    @Transactional
    void testGetAllDoctors() {
        Doctor doctor1 = new Doctor();
        doctor1.setName("Doctor One");
        doctor1.setSpecialty(specialty1);
        doctorRepository.save(doctor1);

        Doctor doctor2 = new Doctor();
        doctor2.setName("Doctor Two");
        doctor2.setSpecialty(specialty2);
        doctorRepository.save(doctor2);

        List<Doctor> actualDoctors = doctorService.getAllDoctors();
        assertEquals(2, actualDoctors.size());
        assertEquals("Doctor One", actualDoctors.get(0).getName());
        assertEquals("Doctor Two", actualDoctors.get(1).getName());
    }

    @Test
    @Transactional
    void testAddDoctor() {
        Doctor newDoctor = new Doctor();
        newDoctor.setName("Random Doctor");
        newDoctor.setSpecialty(specialty1);

        doctorService.addDoctor(newDoctor);

        Doctor savedDoctor = doctorRepository.findDoctorByName("Random Doctor");
        assertEquals("Random Doctor", savedDoctor.getName());
        assertEquals(specialty1.getId(), savedDoctor.getSpecialty().getId());
    }

    @Test
    @Transactional
    void testAddDoctor_existingDoctor() {
        Doctor existingDoctor = new Doctor();
        existingDoctor.setName("Some Doctor");
        existingDoctor.setSpecialty(specialty1);
        doctorRepository.save(existingDoctor);

        doctorService.addDoctor(existingDoctor);

        long count = doctorRepository.count();
        assertEquals(1, count);
    }

    @Test
    @Transactional
    void testDeleteDoctor() {
        Doctor doctorToDelete = new Doctor();
        doctorToDelete.setName("Doctor To Delete");
        doctorToDelete.setSpecialty(specialty1);
        doctorRepository.save(doctorToDelete);
        int id = doctorToDelete.getId();

        doctorService.deleteDoctor(id);

        assertEquals(0, doctorRepository.count());
    }

    @Test
    @Transactional
    void testGetDoctorsBySpecialty() {
        Doctor doctor1 = new Doctor();
        doctor1.setName("Doctor One");
        doctor1.setSpecialty(specialty1);
        doctorRepository.save(doctor1);

        Doctor doctor2 = new Doctor();
        doctor2.setName("Doctor Two");
        doctor2.setSpecialty(specialty1);
        doctorRepository.save(doctor2);

        List<Doctor> doctors = doctorService.getDoctorsBySpecialty(specialty1);
        assertEquals(2, doctors.size());
        assertEquals(specialty1.getId(), doctors.get(0).getSpecialty().getId());
        assertEquals(specialty1.getId(), doctors.get(1).getSpecialty().getId());
    }

    @Test
    @Transactional
    void testGetDoctorById() {
        Doctor doctor1 = new Doctor();
        doctor1.setName("Doctor One");
        doctor1.setSpecialty(specialty1);
        doctorRepository.save(doctor1);

        Doctor actualDoctor = doctorService.getDoctorById(doctor1.getId());
        assertEquals("Doctor One", actualDoctor.getName());
        assertEquals(specialty1.getId(), actualDoctor.getSpecialty().getId());
    }

    @Test
    @Transactional
    void testGetDoctorById_notFound() {
        RuntimeException exception = assertThrows(RuntimeException.class, () -> doctorService.getDoctorById(1000));
        assertEquals("Doctor not found", exception.getMessage());
    }

    @Test
    @Transactional
    void testUpdateDoctor() {
        Doctor existingDoctor = new Doctor();
        existingDoctor.setName("Some Doctor");
        existingDoctor.setEmailAddress("doctor.email@mail.com");
        existingDoctor.setSpecialty(specialty1);
        doctorRepository.save(existingDoctor);

        Doctor updatedDoctor = new Doctor();
        updatedDoctor.setId(existingDoctor.getId());
        updatedDoctor.setName(existingDoctor.getName());
        updatedDoctor.setSpecialty(existingDoctor.getSpecialty());
        updatedDoctor.setEmailAddress("doctor.newEmail@example.com");

        doctorService.updateDoctor(updatedDoctor);

        Doctor retrievedDoctor = doctorRepository.findById(existingDoctor.getId()).orElse(null);
        assertEquals("doctor.newEmail@example.com", retrievedDoctor.getEmailAddress());
    }

    @Test
    @Transactional
    void testUpdateDoctor_notFound() {
        Doctor updatedDoctor = new Doctor();
        updatedDoctor.setId(1000);
        RuntimeException exception = assertThrows(RuntimeException.class, () -> doctorService.updateDoctor(updatedDoctor));
        assertEquals("Doctor with id 1000 not found.", exception.getMessage());
    }
}