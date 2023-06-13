package com.dimash.securityApp.controller;

import com.dimash.securityApp.security.PersonDetails;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class HelloController {
    @GetMapping("/hello")
    public String sayHello() {
        return "hello";
    }

    @GetMapping("/showUserInfo")
    public String showUserInfo() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        PersonDetails personDetails = (PersonDetails) authentication.getPrincipal(); // получим принципал
        System.out.println(personDetails.getPerson());

        return "hello";
        // получим доступ к тому что мы положили в методе AuthProvider => in UserNameAuthenicationToken in first
        // parametr
    }

    @GetMapping("/admin")
    public String adminPage() {
        return "admin";
    }
}
