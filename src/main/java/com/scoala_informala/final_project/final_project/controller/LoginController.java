package com.scoala_informala.final_project.final_project.controller;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.Collection;

@Controller
@RequestMapping("/login")
public class LoginController {

    @GetMapping("/post-login")
    public String handleLogin(Authentication authentication) {
        Collection<? extends GrantedAuthority> authorities = authentication.getAuthorities();

        for (GrantedAuthority authority : authorities) {
            String authorityName = authority.getAuthority();
            if ("ROLE_PATIENT".equals(authorityName)) {
                return "patientStartPage.html";
            } else if ("ROLE_ADMIN".equals(authorityName)) {
                return "adminStartPage.html";
            }
        }

        return "redirect:/";
    }
}
