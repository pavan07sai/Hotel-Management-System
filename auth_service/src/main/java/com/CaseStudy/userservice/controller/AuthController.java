package com.CaseStudy.userservice.controller;

import com.CaseStudy.userservice.model.User;
import com.CaseStudy.userservice.service.AuthService;
import com.CaseStudy.userservice.util.JwtUtil;
import io.jsonwebtoken.Claims;
import jakarta.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;
@RestController
@RequestMapping("/auth")
public class AuthController {

    @Autowired
    private AuthService authService;

    @Autowired
    private JwtUtil jwtUtil;

    @PostMapping("/register")
    public ResponseEntity<?> register(@Valid  @RequestBody User user, @RequestHeader(value = "Authorization", required = false) String token) {
        if (user.getRole().equalsIgnoreCase("OWNER")) {
            if (authService.ownerExists()) {
                return ResponseEntity.badRequest().body("Owner already exists. Only one owner allowed.");
            }
            return ResponseEntity.ok(authService.register(user));
        }

        // For Manager or Receptionist, check if the requester is an Owner
        if (token == null || !token.startsWith("Bearer ")) {
            return ResponseEntity.status(403).body("Authorization token required to register this role.");
        }

        token = token.substring(7);
        Claims claims = jwtUtil.extractClaims(token);
        String role = claims.get("role", String.class);

        if ("OWNER".equalsIgnoreCase(role)) {
            return ResponseEntity.status(403).body("Only Owner can register Manager or Receptionist.");
        }

        return ResponseEntity.ok(authService.register(user));
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@Valid @RequestBody User user) {
        String token = authService.login(user);
        return ResponseEntity.ok(Map.of("token", token));
    }

    @GetMapping("/profile")
    public ResponseEntity<?> getProfile(@RequestHeader("Authorization") String token) {
        if (token.startsWith("Bearer ")) {
            token = token.substring(7);
        }
        Claims claims = jwtUtil.extractClaims(token);
        String username = claims.getSubject();
        String role = claims.get("role", String.class);

        return ResponseEntity.ok(Map.of("username", username, "role", role));
    }
}
