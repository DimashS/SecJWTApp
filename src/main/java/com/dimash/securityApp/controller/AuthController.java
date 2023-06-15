package com.dimash.securityApp.controller;

import com.dimash.securityApp.models.Person;
import com.dimash.securityApp.service.RegistrationService;
import com.dimash.securityApp.util.PersonValidator;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@Controller
@RequestMapping("/auth")
public class AuthController {
    private final RegistrationService registrationService;
    private final PersonValidator personValidator;

    @Autowired
    public AuthController(RegistrationService registrationService, PersonValidator personValidator) {
        this.registrationService = registrationService;
        this.personValidator = personValidator;
    }

    @GetMapping("/login")
    public String loginPage() {
        return "login";
    }

    @GetMapping("/registration")
    public String registrationPage(@ModelAttribute("person") Person person) {
        // @ModelAttribute идет вместо явного model.addAttribute
        return "registration";
    }

    @PostMapping("/registration")
    public String performRegistration(@ModelAttribute("person") @Valid Person person, BindingResult bindingResult) {
        // valid => for validation through html
        personValidator.validate(person, bindingResult);
        if (bindingResult.hasErrors())
            return "/registration";
        registrationService.register(person);
        return "redirect:/login";
    }
}