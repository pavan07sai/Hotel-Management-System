package com.CaseStudy.userservice.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.CaseStudy.userservice.model.User; 
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
	Optional<User> findByUsername(String username);
	boolean existsByRole(String role);
}
