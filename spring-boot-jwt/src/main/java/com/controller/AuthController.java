package com.controller;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.model.Student;

@RestController
public class AuthController 
{
	
	
   @GetMapping(value = "/student/getStudent", produces = "application/json")
   public Student getStudentDetails() {
     Student stu=new Student();
     stu.setStudId(101);
     stu.setStudName("John");
     return stu;
 }

	@RequestMapping("/login")
	public String login() {
		return "Successfully logged in!!";
	}
}
