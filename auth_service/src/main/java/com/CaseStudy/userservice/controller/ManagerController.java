package com.CaseStudy.userservice.controller;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/manager")
public class ManagerController {

    @GetMapping("/dashboard")
    @PreAuthorize("hasAnyRole('MANAGER', 'OWNER')")
    public String getManagerDashboard() {
        return "Welcome to the Manager Dashboard!";
    }
}
