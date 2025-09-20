package com.example.dating_app.controller;

import com.example.dating_app.dto.UserResponseDTO;
import com.example.dating_app.model.User;
import com.example.dating_app.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.*;
import com.example.dating_app.dto.RecommendationFilterDTO;
import com.example.dating_app.service.RecommendationService;

import java.util.List;

@RestController
@RequestMapping("/api/recommend")
@RequiredArgsConstructor
public class RecommendationController {
    private final RecommendationService recommendationService;
    private final UserService userService;

    @GetMapping
    public List<UserResponseDTO> findAll(@ModelAttribute RecommendationFilterDTO filter) {
        // Здесь можно добавить пагинацию
        return userService.findAll(filter);
    }
}
