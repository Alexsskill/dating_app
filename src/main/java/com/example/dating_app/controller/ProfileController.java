package com.example.dating_app.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.example.dating_app.dto.ProfileUpdateDTO;
import com.example.dating_app.service.CurrentUserService;

@RestController
@RequestMapping("/api/profile")
@RequiredArgsConstructor
public class ProfileController {
    private final CurrentUserService currentUserService;

    @GetMapping
    public ResponseEntity<?> getProfile() {
        return ResponseEntity.ok(currentUserService.getCurrentUser());
    }

    @PutMapping
    public ResponseEntity<?> updateProfile(@RequestBody ProfileUpdateDTO dto) {
        return ResponseEntity.ok(currentUserService.updateProfile(dto));
    }
}