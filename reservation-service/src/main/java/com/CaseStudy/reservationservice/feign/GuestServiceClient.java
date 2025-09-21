package com.CaseStudy.reservationservice.feign;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import com.CaseStudy.reservationservice.dto.GuestDTO;

@FeignClient(name = "guest-service")  //
public interface GuestServiceClient {

    @GetMapping("/guests/getby/{id}")  
    GuestDTO getGuestById(@PathVariable Long id);  
}