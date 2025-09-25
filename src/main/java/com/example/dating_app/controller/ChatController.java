package com.example.dating_app.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.web.bind.annotation.*;
import com.example.dating_app.dto.ChatMessageDTO;
import com.example.dating_app.service.ChatService;

import java.util.List;

@RestController
@RequestMapping("/api/chat")
@RequiredArgsConstructor
public class ChatController {
    private final ChatService chatService;

    @PostMapping("/send")
    public ResponseEntity<String> sendMessage(@RequestBody ChatMessageDTO dto) {
        chatService.sendMessage(dto.getSenderId(), dto.getReceiverId(), dto.getContent());
        return ResponseEntity.ok("Сообщение отправлено");
    }

    @GetMapping("/history/{userId}/{otherId}")
    public ResponseEntity<List<ChatMessageDTO>> getHistory(@PathVariable Long userId, @PathVariable Long otherId) {
        var messages = chatService.getChatHistory(userId, otherId);
        return ResponseEntity.ok(messages.stream()
                .map(m -> ChatMessageDTO.builder()
                        .senderId(m.getSender().getId())
                        .receiverId(m.getReceiver().getId())
                        .content(m.getContent())
                        .sentAt(m.getSentAt())
                        .build())
                .toList());
    }
}