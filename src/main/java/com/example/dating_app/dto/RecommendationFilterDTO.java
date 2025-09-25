package com.example.dating_app.dto;

import lombok.*;

@Data
@Builder
public class RecommendationFilterDTO {
    private String gender;
    private Integer minAge;
    private Integer maxAge;
    private String city;
    private String lookingFor;
}
