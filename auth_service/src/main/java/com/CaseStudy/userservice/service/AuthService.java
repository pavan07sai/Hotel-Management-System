package com.CaseStudy.userservice.service;

import com.CaseStudy.userservice.model.User;
import com.CaseStudy.userservice.repository.UserRepository;
import com.CaseStudy.userservice.util.JwtUtil;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.authorization.AuthenticatedAuthorizationManager;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.http.HttpStatus;

@Service
public class AuthService {

    @Autowired
    private UserRepository userRepository;
    
    
    @Autowired
    private JwtUtil jwtUtil;
    
    @Autowired
    private AuthenticationManager authenticationManager;
    
    private final BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

    public User register(User user) {
        if (userRepository.findByUsername(user.getUsername()).isPresent()) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Username already taken");
        }

        if (user.getRole() == null) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Role is required");
        }

        user.setPassword(passwordEncoder.encode(user.getPassword()));

        return userRepository.save(user);
    }

    public String login(User user) {
    	try {
    	Authentication authenticate =authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(user.getUsername(), user.getPassword()));
    	if(authenticate.isAuthenticated()) {
    		User userDetails=userRepository.findByUsername(user.getUsername()).get();
        return jwtUtil.generateToken(userDetails.getUsername(), userDetails.getRole()); 
    	}
    	else {
//    		throw new RuntimeException("wrong credentails");
    		System.out.println("wrong credentails");
    		return "wrong credentails";
    	}}catch(Exception e) {
    		e.printStackTrace();
    		System.out.println("wrong credentails");
    		return "wrong credentails";
    	}
    	// Include role in JWT
    }
    
    public boolean ownerExists() {
        return userRepository.existsByRole("OWNER");
    }

}
