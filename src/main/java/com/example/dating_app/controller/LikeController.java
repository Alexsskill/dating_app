package com.example.dating_app.controller;

import com.example.dating_app.dto.LikeResponseDTO;
import com.example.dating_app.service.LikeService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("api/like")
public class LikeController {
    private final LikeService likeService;

    @PostMapping
    public ResponseEntity<String> like(Long targetUserId) {
        likeService.like(targetUserId);
        return ResponseEntity.ok("Лайк успешно поставлен");
    }

    @GetMapping
    public ResponseEntity<List<LikeResponseDTO>> getLikeUsers() {
        return ResponseEntity.ok(likeService.getLikeUsers());
    }

    @GetMapping(path = "{id}")
    public ResponseEntity<String> unlike( @PathVariable Long id) {
        likeService.unlike(id);
        return ResponseEntity.ok("Лайк успешно убран");
    }
}
