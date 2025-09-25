package com.example.dating_app.service;

import com.example.dating_app.dto.UserResponseDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import com.example.dating_app.dto.RecommendationFilterDTO;

import java.util.List;

@Service
@RequiredArgsConstructor
public class RecommendationService {
    private final UserService userService;

    public List<UserResponseDTO> getRecommendations(RecommendationFilterDTO filter) {
        return userService.findAll(filter);

    }
}