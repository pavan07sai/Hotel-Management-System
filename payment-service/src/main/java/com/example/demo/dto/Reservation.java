package com.example.demo.dto;

import jakarta.persistence.*;


public class Reservation {

    private Long id;

    private Long guestId; // Foreign key to Guest
    private Long roomId;  // Foreign key to Room
    private String checkInDate;
    private String checkOutDate;
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
    
    @Override
	public String toString() {
		return "Reservation [id=" + id + ", "
				+ "guestId=" + guestId + ","
				+ " roomId=" + roomId + ", "
				+ "checkInDate=" + checkInDate
				+ ", checkOutDate=" + checkOutDate + ", "
				+ "status=" + status + "]";
	}
}