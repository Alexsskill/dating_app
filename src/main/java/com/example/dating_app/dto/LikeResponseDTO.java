package com.example.dating_app.dto;

import lombok.*;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class LikeResponseDTO {
    private Long id;
    private Long userId;
    private String userName;
    private Integer userAge;
    private String userCity;
    private String userGender;
    private String userPhone; // Добавьте это поле
    private String lookingFor; // Добавьте это поле
}