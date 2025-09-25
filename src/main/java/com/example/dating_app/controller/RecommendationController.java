package com.example.dating_app.controller;

import com.example.dating_app.dto.UserResponseDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import com.example.dating_app.dto.RecommendationFilterDTO;
import com.example.dating_app.service.RecommendationService;

import java.util.List;

@RestController
@RequestMapping("/api/recommend")
@RequiredArgsConstructor
public class RecommendationController {
    private final RecommendationService recommendationService;

    @GetMapping
    public List<UserResponseDTO> findAll(@ModelAttribute RecommendationFilterDTO filter) {
        return recommendationService.getRecommendations(filter);
    }
}
