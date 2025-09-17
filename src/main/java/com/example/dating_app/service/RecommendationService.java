package com.example.dating_app.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import com.example.dating_app.dto.RecommendationFilterDTO;
import com.example.dating_app.model.User;
import com.example.dating_app.repository.UserRepository;
import com.example.dating_app.specification.UserSpecification;

import java.util.List;

@Service
@RequiredArgsConstructor
public class RecommendationService {
    private final UserRepository userRepository;

    public List<User> getRecommendations(RecommendationFilterDTO filter) {
        var spec = UserSpecification.createRecommendationSpec(filter);
        return userRepository.findAll(spec);
    }
}