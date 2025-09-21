package com.CaseStudy.reservationservice.feign;

import com.CaseStudy.reservationservice.dto.RoomDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "ROOM-SERVICE")
public interface RoomServiceClient {
    @GetMapping("/rooms/getby/{id}")
    RoomDTO getRoomById(@PathVariable Long id);
}
