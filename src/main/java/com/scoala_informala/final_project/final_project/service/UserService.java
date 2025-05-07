package com.scoala_informala.final_project.final_project.service;

import com.scoala_informala.final_project.final_project.config.SecurityConfig;
import com.scoala_informala.final_project.final_project.model.Patient;
import com.scoala_informala.final_project.final_project.model.User;
import com.scoala_informala.final_project.final_project.repository.PatientRepository;
import com.scoala_informala.final_project.final_project.repository.UserRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserService implements UserDetailsService {

    private final UserRepository userRepository;
    private final PatientRepository patientRepository;
    private final SecurityConfig securityConfig;

    @Transactional
    public User registerUser(String username, String password, String patientName, String phoneNumber) {

        if (userRepository.findByUsername(username).isPresent()) {
            throw new IllegalArgumentException("Username already exists");
        }

        User user = new User();
        user.setUsername(username);
        user.setPassword(securityConfig.delegatingPasswordEncoder().encode(password));
        user.setRole("ROLE_PATIENT");
        createPatient(user, patientName, phoneNumber);
        return userRepository.save(user);
    }

    private Patient createPatient(User user, String name, String phoneNumber) {
        Patient patient = new Patient();
        patient.setUser(user);
        patient.setName(name);
        patient.setPhoneNumber(phoneNumber);
        return patientRepository.save(patient);
    }

    @Override
    public UserDetails loadUserByUsername(final String username) throws UsernameNotFoundException {
        User foundUser = userRepository.findByUsername(username)
                .orElseThrow(() -> new RuntimeException("User not found by username: " + username));

        return foundUser;
    }

    public User findByUsername(String username) {
        return userRepository.findByUsername(username).orElseThrow(() -> new RuntimeException("User with username " + username + " not found."));
    }

    public Patient findPatientByUser(User user) {
        int patientId = user.getId();
        return patientRepository.findById(patientId).orElseThrow(() -> new RuntimeException("Patient with id " + patientId + " not found."));
    }
}
