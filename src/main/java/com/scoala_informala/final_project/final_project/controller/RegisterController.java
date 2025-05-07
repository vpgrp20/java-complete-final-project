package com.scoala_informala.final_project.final_project.controller;

import com.scoala_informala.final_project.final_project.model.User;
import com.scoala_informala.final_project.final_project.service.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.ui.Model;

@Controller
@RequiredArgsConstructor
@RequestMapping("/register")
public class RegisterController {

    private final UserService userService;

    @GetMapping
    public String getRegisterUserForm(Model model) {
        model.addAttribute("user", new User());
        return "registerForm";
    }

    @PostMapping
    public String registerUser(@ModelAttribute @Valid User user, BindingResult bindingResult, String name, String phoneNumber, Model model) {
        if (bindingResult.hasErrors()) {
            return "registerForm";
        }

        try {
            final User registeredUser = userService.registerUser(user.getUsername(), user.getPassword(), name, phoneNumber);
        } catch (IllegalArgumentException e) {
            model.addAttribute("errorMessage", e.getMessage());
            return "registerForm";
        }

        return "redirect:/";
    }
}
