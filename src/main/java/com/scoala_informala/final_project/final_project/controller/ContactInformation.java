package com.scoala_informala.final_project.final_project.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/contact-us")
public class ContactInformation {

    @GetMapping
    public String getContactInformation(Model model) {
        final String address = "Some random address no. 123";
        final String phonenumber = "(0260)-123-456";

        model.addAttribute("address", address);
        model.addAttribute("phonenumber", phonenumber);

        return "contactInformation.html";
    }
}
