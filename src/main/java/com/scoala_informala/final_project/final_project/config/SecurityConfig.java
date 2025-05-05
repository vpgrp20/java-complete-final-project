package com.scoala_informala.final_project.final_project.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@EnableWebSecurity
@Configuration
public class SecurityConfig {

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
                .csrf(c -> c.disable())
                .httpBasic(Customizer.withDefaults())
//                .formLogin(Customizer.withDefaults())
                .formLogin(form -> form
                        .defaultSuccessUrl("/login/post-login", true)
                        .permitAll()
                )
                .authorizeHttpRequests(authorize -> authorize
//                        .requestMatchers("/").permitAll()
//                        .requestMatchers("/info").permitAll()
//                        .requestMatchers("/contact-us").permitAll()
//                        .requestMatchers("/appointment/specialties").hasRole("PATIENT")
//                        .requestMatchers("/appointment/book").hasRole("PATIENT")
//                        .requestMatchers("/doctor/**").hasRole("ADMIN")
//                        .requestMatchers("/specialty/**").hasRole("ADMIN")
//                        .requestMatchers("/patient/**").hasRole("ADMIN")
                        .anyRequest().permitAll()
                );
        return http.build();
    }

    @Bean
    public PasswordEncoder delegatingPasswordEncoder() {
        return new BCryptPasswordEncoder();
    }
}
