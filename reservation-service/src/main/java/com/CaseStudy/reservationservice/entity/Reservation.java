package com.CaseStudy.reservationservice.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

@Entity
public class Reservation {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    

    @NotNull(message = "Guest ID must not be null")
    private Long guestId;// Foreign key to Guest

    @NotNull(message = "Room ID must not be null")
    private Long roomId;  // Foreign key to Room
    

    @NotBlank(message = "Check-in date must not be empty")
    private String checkInDate;
    
    @NotBlank(message = "Check-out date must not be empty")
    private String checkOutDate;
    
    @NotBlank(message = "Status must not be empty")
    private String status; // e.g., "Pending", "Confirmed", "Cancelled"

    // Default Constructor
    public Reservation() {
    }

    // Parameterized Constructor
    public Reservation(Long guestId, Long roomId, String checkInDate, String checkOutDate, String status) {
        this.guestId = guestId;
        this.roomId = roomId;
        this.checkInDate = checkInDate;
        this.checkOutDate = checkOutDate;
        this.status = status;
    }

    // Getters and Setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getGuestId() {
        return guestId;
    }

    public void setGuestId(Long guestId) {
        this.guestId = guestId;
    }

    public Long getRoomId() {
        return roomId;
    }

    public void setRoomId(Long roomId) {
        this.roomId = roomId;
    }

    public String getCheckInDate() {
        return checkInDate;
    }

    public void setCheckInDate(String checkInDate) {
        this.checkInDate = checkInDate;
    }

    public String getCheckOutDate() {
        return checkOutDate;
    }

    public void setCheckOutDate(String checkOutDate) {
        this.checkOutDate = checkOutDate;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}