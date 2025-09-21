package com.CaseStudy.reservationservice.repository;

import com.CaseStudy.reservationservice.entity.Reservation;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ReservationRepository extends JpaRepository<Reservation, Long> {
	Optional<Reservation> findByGuestId(Long guestId);
	 
}