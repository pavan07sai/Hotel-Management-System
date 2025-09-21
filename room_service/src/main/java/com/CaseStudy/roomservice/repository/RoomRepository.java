package com.CaseStudy.roomservice.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.CaseStudy.roomservice.entity.Room;
@Repository
public interface RoomRepository extends JpaRepository<Room, Long> {
    // Custom query methods can be added here
	Optional<Room> findByRoomNumber(String roomNumber);

}