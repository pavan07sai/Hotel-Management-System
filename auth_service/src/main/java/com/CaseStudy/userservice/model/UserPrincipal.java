package com.CaseStudy.userservice.model;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

public class UserPrincipal implements UserDetails{

	private String username;
	private String password;
	private List<SimpleGrantedAuthority> authorities;
	
	public UserPrincipal(User user) {
		super();
		this.username = user.getUsername();
		this.password = user.getPassword();
		this.authorities = Arrays.asList(user.getRole()).stream()
						.map(role->role.startsWith("ROLE_") ? role : "ROLE_"+role)
						.map(SimpleGrantedAuthority::new).toList();
	}

	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		return authorities;
	}

	@Override
	public String getPassword() {
		return password;
	}

	@Override
	public String getUsername() {
		return username;
	}

}
