package com.example.dating_app.controller;

import com.example.dating_app.dto.ChatMessageResponseDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.example.dating_app.dto.ChatMessageCreateDTO;
import com.example.dating_app.service.ChatService;

import java.util.List;

@RestController
@RequestMapping("/api/chat")
@RequiredArgsConstructor
public class ChatController {
    private final ChatService chatService;

    @PostMapping("/send")
    public ResponseEntity<String> sendMessage(@RequestBody ChatMessageCreateDTO dto) {
        chatService.sendMessage(dto);
        return ResponseEntity.ok("Сообщение отправлено");
    }

    @GetMapping("/history/{currentUserId}/{otherUserId}")
    public ResponseEntity<List<ChatMessageResponseDTO>> getChatHistory(
            @PathVariable Long currentUserId,
            @PathVariable Long otherUserId) {
        return ResponseEntity.ok(chatService.getChatHistory(currentUserId, otherUserId));
    }
}