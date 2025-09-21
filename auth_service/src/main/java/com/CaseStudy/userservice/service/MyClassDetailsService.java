package com.CaseStudy.userservice.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.CaseStudy.userservice.model.User;
import com.CaseStudy.userservice.model.UserPrincipal;
import com.CaseStudy.userservice.repository.UserRepository;

@Service
public class MyClassDetailsService implements UserDetailsService{

	@Autowired
	private UserRepository userRepository;
	
	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

		User user=userRepository.findByUsername(username).get();
		if(user==null) {
			throw new RuntimeException("user not found with username: "+username);
		}
		
		return new UserPrincipal(user);
	}

}
