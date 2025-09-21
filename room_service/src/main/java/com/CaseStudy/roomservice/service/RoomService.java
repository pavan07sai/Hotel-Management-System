package com.CaseStudy.roomservice.service;

import com.CaseStudy.roomservice.entity.Room;
import com.CaseStudy.roomservice.repository.RoomRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class RoomService {

    @Autowired
    private RoomRepository roomRepository;

    // Add a new room
    public Room addRoom(Room room) {

    	Optional<Room> existingRoom = roomRepository.findByRoomNumber(room.getRoomNumber());
    		if (existingRoom.isPresent()) {
    			throw new IllegalArgumentException("Room with number " + room.getRoomNumber() + " already exists.");
    			}
    		return roomRepository.save(room);

    }

    // Get all rooms
    public List<Room> getAllRooms() {
        return roomRepository.findAll();
    }

    // Get a room by ID
    public Room getRoomById(Long id) {
        return roomRepository.findById(id).orElse(null);
    }
    
    // Delete a room by ID
    
    public void deleteRoom(Long id) {
        if (!roomRepository.existsById(id)) {
            throw new RuntimeException("Room with ID " + id + " not found.");
        }
        roomRepository.deleteById(id);
    }

 // Update a room
    public Room updateRoom(Long id, Room updatedRoom) {
        Room existingRoom = roomRepository.findById(id).orElse(null);
        if (existingRoom != null) {
            existingRoom.setRoomNumber(updatedRoom.getRoomNumber());
            existingRoom.setType(updatedRoom.getType());
            existingRoom.setPrice(updatedRoom.getPrice());
            existingRoom.setAvailable(updatedRoom.isAvailable());
            return roomRepository.save(existingRoom);
        } else {
            return null; // Or handle this with an exception
        }
    }

}