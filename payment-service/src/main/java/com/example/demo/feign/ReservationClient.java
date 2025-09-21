package com.example.demo.feign;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import com.example.demo.dto.Reservation;

@FeignClient("RESERVATION-SERVICE")
public interface ReservationClient {

    @GetMapping("/reservations/getby/{id}")
    Reservation getReservationById(@PathVariable("id") Long id);
    
    @GetMapping("reservations/getbyguestid/{guestId}")
	public Reservation getReservationGuestId(@PathVariable Long guestId);
}

