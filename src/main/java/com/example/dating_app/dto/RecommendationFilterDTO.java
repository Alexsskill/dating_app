package com.example.dating_app.dto;

import lombok.*;
import java.time.LocalDate;

@Data
@Builder
public class RecommendationFilterDTO {
    private String gender;
    private Integer minAge;
    private Integer maxAge;
    private Double maxDistanceKm;
    private LocalDate dateOfBirthFrom;
    private LocalDate dateOfBirthTo;
}
