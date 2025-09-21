package com.CaseStudy.reservationservice.service;

import com.CaseStudy.reservationservice.dto.GuestDTO;
import com.CaseStudy.reservationservice.dto.RoomDTO;
import com.CaseStudy.reservationservice.entity.Reservation;
import com.CaseStudy.reservationservice.feign.GuestServiceClient;
import com.CaseStudy.reservationservice.feign.RoomServiceClient;
import com.CaseStudy.reservationservice.repository.ReservationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ReservationService {

    @Autowired
    private ReservationRepository reservationRepository;

    @Autowired
    private RoomServiceClient roomServiceClient;

    @Autowired
    private GuestServiceClient guestServiceClient;

    public Reservation addReservation(Reservation reservation) {
        // Validate guest and room
        GuestDTO guest = guestServiceClient.getGuestById(reservation.getGuestId());
        if (guest == null) {
            throw new RuntimeException("Guest not found with ID: " + reservation.getGuestId());
        }

        RoomDTO room = roomServiceClient.getRoomById(reservation.getRoomId());
        if (room == null) {
            throw new RuntimeException("Room not found with ID: " + reservation.getRoomId());
        }

        // Check if the room is available
        if (!room.isAvailable()) {
            throw new RuntimeException("Room is not available");
        }

        return reservationRepository.save(reservation);
    }


    public List<Reservation> getAllReservations() {
        return reservationRepository.findAll();
    }

    public Reservation getReservationById(Long id) {
        return reservationRepository.findById(id).orElse(null);
    }

    public Reservation updateReservation(Reservation reservation) {
        return reservationRepository.save(reservation);
    }

    public void deleteReservation(Long id) {
        reservationRepository.deleteById(id);	
    }


	public Optional<Reservation> getReservationGuestId(Long guestId) {
		return reservationRepository.findByGuestId(guestId);
		
	}
}