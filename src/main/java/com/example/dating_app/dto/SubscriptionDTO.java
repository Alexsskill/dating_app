package com.example.dating_app.dto;

import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class SubscriptionDTO {
    private String type;
    private LocalDateTime startDate;
    private LocalDateTime endDate;
    private Boolean isActive;
}