package com.example.dating_app.dto;

import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ChatMessageDTO {
    private Long senderId;
    private Long receiverId;
    private String content;
    private LocalDateTime sentAt;
}