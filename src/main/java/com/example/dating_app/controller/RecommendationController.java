package com.example.dating_app.controller;

import com.example.dating_app.model.User;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.*;
import com.example.dating_app.dto.RecommendationFilterDTO;
import com.example.dating_app.service.RecommendationService;

@RestController
@RequestMapping("/api/recommend")
@RequiredArgsConstructor
public class RecommendationController {
    private final RecommendationService recommendationService;

    @GetMapping
    public Page<User> get(@ModelAttribute RecommendationFilterDTO filter, Pageable pageable) {
        // Здесь можно добавить пагинацию
        return Page.empty(); // заглушка
    }
}
