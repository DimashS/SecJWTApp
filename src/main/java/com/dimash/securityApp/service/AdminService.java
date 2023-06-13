package com.dimash.securityApp.service;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;

@Service
public class AdminService {
@PreAuthorize("hasRole('ROLE_ADMIN')")
    public void sayAdmin() {
        System.out.println("It's me admin");
    }
}
