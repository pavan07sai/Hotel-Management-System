package com.CaseStudy.roomservice.controller;
 
import com.CaseStudy.roomservice.dto.RoomDTO;
import com.CaseStudy.roomservice.entity.Room;
import com.CaseStudy.roomservice.repository.RoomRepository;
import com.CaseStudy.roomservice.service.RoomService;

import io.swagger.v3.oas.annotations.Operation;
import jakarta.validation.Valid;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/rooms")
public class RoomController {

    @Autowired
    private RoomService roomService;
    
  
    @PostMapping("/add")
    public Room addRoom(@Valid @RequestBody Room room) {
        return roomService.addRoom(room);
      }


    
    @PutMapping("/update/{id}")
    public Room updateRoom( @PathVariable Long id, @Valid @RequestBody Room room) {
        return roomService.updateRoom(id, room);
    }

    
    
    @DeleteMapping("/delete/{id}")
    public String deleteRoom(@PathVariable Long id) {
        roomService.deleteRoom(id);
        return "Room deleted successfully!";
    }

    // Accessible by OWNER, MANAGER, and RECEPTIONIST
    @GetMapping("/getall")
    public List<Room> getAllRooms() {
        return roomService.getAllRooms();
    }
    
    @GetMapping("/getby/{id}")
    public RoomDTO getRoomById(@PathVariable Long id) {
        Room room = roomService.getRoomById(id);
        return new RoomDTO(
            room.getId(),
            room.getRoomNumber(),
            room.getType(),
            room.getPrice(),
            room.isAvailable()
        );
    }
}
