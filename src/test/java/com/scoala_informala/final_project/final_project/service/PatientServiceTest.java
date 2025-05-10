package com.scoala_informala.final_project.final_project.service;

import com.scoala_informala.final_project.final_project.model.Patient;
import com.scoala_informala.final_project.final_project.model.dto.PatientDto;
import com.scoala_informala.final_project.final_project.model.dto.PatientUpdateDto;
import com.scoala_informala.final_project.final_project.repository.PatientRepository;
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
@Import(PatientService.class)
@ActiveProfiles("test")
public class PatientServiceTest {

    private final PatientRepository patientRepository;
    private final PatientService patientService;

    @Autowired
    PatientServiceTest(final PatientRepository patientRepository, final PatientService patientService) {
        this.patientRepository = patientRepository;
        this.patientService = patientService;
    }

    private Patient patient1;
    private Patient patient2;

    @BeforeEach
    void setUp() {
        patient1 = new Patient();
        patient1.setName("Patient One");
        patientRepository.save(patient1);

        patient2 = new Patient();
        patient2.setName("Patient Two");
        patientRepository.save(patient2);
    }

    @Test
    @Transactional
    void testDeletePatient() {
        Patient patientToDelete = new Patient();
        patientRepository.save(patientToDelete);
        int id = patientToDelete.getId();

        assertEquals(3, patientRepository.count());

        patientService.deletePatient(id);

        assertEquals(2, patientRepository.count());
    }

    @Test
    @Transactional
    void testGetAllPatients() {
        List<PatientDto> actualDtos = patientService.getAllPatients();
        assertEquals(2, actualDtos.size());
        assertEquals("Patient One", actualDtos.get(0).getName());
        assertEquals("Patient Two", actualDtos.get(1).getName());
    }

    @Test
    @Transactional
    void testGetPatientById() {
        Patient patient = new Patient();
        patient.setPhoneNumber("0712341234");
        patientRepository.save(patient);

        PatientUpdateDto actualDto = patientService.getPatientById(patient.getId());
        assertEquals(patient.getId(), actualDto.getId());
        assertEquals("0712341234", actualDto.getPhoneNumber());
    }

    @Test
    @Transactional
    void testGetPatientById_notFound() {
        RuntimeException exception = assertThrows(RuntimeException.class, () -> patientService.getPatientById(1000));
        assertEquals("Patient with id 1000 not found.", exception.getMessage());
    }

    @Test
    @Transactional
    void testUpdatePatient() {
        Patient existingPatient = new Patient();
        existingPatient.setPhoneNumber("0712341234");
        patientRepository.save(existingPatient);

        PatientUpdateDto patientDto = new PatientUpdateDto();
        patientDto.setId(existingPatient.getId());
        patientDto.setPhoneNumber("0712345678");

        patientService.updatePatient(patientDto);

        Patient updatedPatient = patientRepository.findById(existingPatient.getId()).orElse(null);
        assertEquals("0712345678", updatedPatient.getPhoneNumber());
    }

    @Test
    @Transactional
    void testUpdatePatient_notFound() {
        PatientUpdateDto patientDto = new PatientUpdateDto();
        patientDto.setId(1000);
        patientDto.setPhoneNumber("0712341234");
        RuntimeException exception = assertThrows(RuntimeException.class, () -> patientService.updatePatient(patientDto));
        assertEquals("Patient with id 1000 not found.", exception.getMessage());
    }

    @Test
    @Transactional
    void testDeletePatient_notFound() {
        RuntimeException exception = assertThrows(RuntimeException.class, () -> patientService.deletePatient(1000));
        assertEquals("Patient with id 1000 not found.", exception.getMessage());
    }
}
