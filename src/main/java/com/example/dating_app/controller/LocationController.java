package com.example.dating_app.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import com.example.dating_app.dto.LocationDTO;
import com.example.dating_app.service.LocationService;

@RestController
@RequestMapping("/api/location")
@RequiredArgsConstructor
public class LocationController {
    private final LocationService locationService;

    @PutMapping
    public void updateLocation(@RequestBody LocationDTO dto) {
        Long userId = getCurrentUserId(); // реализуйте через SecurityContext
        locationService.updateLocation(userId, dto.getLatitude(), dto.getLongitude(), dto.getCity());
    }
}
