package com.dimash.securityApp.controller;

import com.dimash.securityApp.security.PersonDetails;
import com.dimash.securityApp.service.AdminService;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class HelloController {
    private final AdminService adminService;

    public HelloController(AdminService adminService) {
        this.adminService = adminService;
    }

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
        adminService.sayAdmin();
        return "admin";
    }
}
