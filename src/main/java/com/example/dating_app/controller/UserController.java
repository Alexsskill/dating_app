package com.example.dating_app.controller;

import com.example.dating_app.dto.UserResponseDTO;
import com.example.dating_app.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("api/user")
public class UserController {
    private final UserService userService;

    @GetMapping(path ="{id}")
    public ResponseEntity<UserResponseDTO> findById(@PathVariable Long id) {
    return ResponseEntity.ok(userService.findById(id));
    }
}
