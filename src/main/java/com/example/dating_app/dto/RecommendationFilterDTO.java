package com.example.dating_app.dto;

import com.example.dating_app.enums.LookingFor;
import lombok.*;
import java.time.LocalDate;

@Data
@Builder
public class RecommendationFilterDTO {
    private String gender;
    private Integer minAge;
    private Integer maxAge;
    private String city;
    private LookingFor lookingFor;
}
