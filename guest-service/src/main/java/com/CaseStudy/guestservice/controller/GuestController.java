package com.CaseStudy.guestservice.controller;

import com.CaseStudy.guestservice.dto.GuestDTO;
import com.CaseStudy.guestservice.entity.Guest;
import com.CaseStudy.guestservice.service.GuestService;

import jakarta.validation.Valid;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/guests")
public class GuestController {

    @Autowired
    private GuestService guestService;
    
    @PostMapping("/add")
    public Guest addRoom(@Valid @RequestBody Guest guest) {
        return guestService.addGuest(guest);
    }
    
    @PutMapping("/update/{id}")
    public Guest updateGuest(@PathVariable Long id,@Valid @RequestBody Guest guest) {
        guest.setId(id); // Ensure ID is correctly set
        return guestService.updateGuest(guest);
    }

    @DeleteMapping("/delete/{id}")
    public String deleteGuest(@PathVariable Long id) {
        guestService.deleteGuest(id);
        return "Guest deleted successfully";
    }
    
    @GetMapping("/getall")
    public List<Guest> getAllGuests() {
        return guestService.getAllGuests();
    }


    @GetMapping("/getby/{id}")
    public GuestDTO getGuestById(@PathVariable Long id) {
        Guest guest = guestService.getGuestById(id);
        return new GuestDTO(
            guest.getId(),
            guest.getName(),
            guest.getEmail(),
            guest.getPhone(),
            guest.getAddress()
        );
    }
}