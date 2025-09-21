package com.CaseStudy.reservationservice.controller;

import com.CaseStudy.reservationservice.dto.Payment;
import com.CaseStudy.reservationservice.dto.RoomDTO;
import com.CaseStudy.reservationservice.entity.Reservation;
import com.CaseStudy.reservationservice.feign.PaymentClient;
import com.CaseStudy.reservationservice.feign.RoomServiceClient;
import com.CaseStudy.reservationservice.service.ReservationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.List;

@RestController
@RequestMapping("/reservations")
public class ReservationController {

	@Autowired
	private ReservationService reservationService;

	@Autowired
	private PaymentClient paymentClient;
	
	@Autowired
	private RoomServiceClient roomServiceClient;
	

	@PostMapping("/reserve")
	public String reserveAndPay(@RequestBody Reservation reservation) {
	    Reservation saved = reservationService.addReservation(reservation);
	    

        RoomDTO roomDTO = roomServiceClient.getRoomById(saved.getRoomId());
        

        LocalDate checkIn = LocalDate.parse(saved.getCheckInDate());
        LocalDate checkOut = LocalDate.parse(saved.getCheckOutDate());
        long days = ChronoUnit.DAYS.between(checkIn, checkOut);

       
  
	    String referenceId = "reservation_" + saved.getId();
        double amount = roomDTO.getPrice()* days; 
	    Payment payment=new Payment();
	    payment.setUsername("guest_" + saved.getGuestId());
	    payment.setAmount(amount);
	    String paymentLink = paymentClient.createPaymentLink(payment);

	    return "Reservation created. Please complete payment at: " + paymentLink;
	}

	@GetMapping("/getall")
	public List<Reservation> getAllReservations() {
		return reservationService.getAllReservations();
	}

	@GetMapping("/getby/{id}")
	public Reservation getReservationById(@PathVariable Long id) {
		return reservationService.getReservationById(id);
	}
	@GetMapping("getbyguestid/{guestId}")
	public Reservation getReservationGuestId(@PathVariable Long guestId) {
		return reservationService.getReservationGuestId(guestId).get();
	}
	
	@PutMapping("/update/{id}")
	public Reservation updateReservation(@PathVariable Long id, @RequestBody Reservation reservation) {
	    reservation.setId(id); // Ensure the ID from the path is set in the object
	    return reservationService.updateReservation(reservation);
	}


	@DeleteMapping("/delete/{id}")
	public String deleteReservation(@PathVariable Long id) {
		reservationService.deleteReservation(id);
		return "Reservation is cancelled";
	}
	
	
}