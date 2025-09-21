package com.CaseStudy.userservice.controller;

import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/receptionist")
public class ReceptionistController {

    @GetMapping("/dashboard")
    public ResponseEntity<String> getReceptionistDashboard(@AuthenticationPrincipal Jwt jwt) {
        System.out.println("JWT Claims: " + jwt.getClaims());
        return ResponseEntity.ok("Receptionist Dashboard Access Granted");
    }
}
