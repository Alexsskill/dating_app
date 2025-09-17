package com.example.dating_app.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.example.dating_app.dto.LikeRequest;
import com.example.dating_app.service.MatchService;

@RestController
@RequestMapping("/api/match")
@RequiredArgsConstructor
public class MatchController {
    private final MatchService matchService;

    @PostMapping("/like")
    public ResponseEntity<String> likeUser(@RequestBody LikeRequest request) {
        return ResponseEntity.ok(matchService.likeUser(request.getTargetUserId()));
    }
}