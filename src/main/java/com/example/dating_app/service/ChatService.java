package com.example.dating_app.service;

import com.example.dating_app.dto.ChatMessageCreateDTO;
import com.example.dating_app.dto.ChatMessageResponseDTO;
import com.example.dating_app.mapper.ChatMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.example.dating_app.exception.UserNotFoundException;
import com.example.dating_app.model.ChatMessage;
import com.example.dating_app.model.User;
import com.example.dating_app.repository.ChatMessageRepository;
import com.example.dating_app.repository.UserRepository;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ChatService {
    private final ChatMessageRepository chatMessageRepository;
    private final UserRepository userRepository;
    private final SimpMessagingTemplate messagingTemplate;
    private final ChatMapper chatMapper;

    @Transactional
    public void sendMessage(ChatMessageCreateDTO chatMessageCreateDTO) {
        User sender = userRepository.findById(chatMessageCreateDTO.getSenderId())
                .orElseThrow(() -> new UserNotFoundException(chatMessageCreateDTO.getSenderId()));
        User receiver = userRepository.findById(chatMessageCreateDTO.getReceiverId())
                .orElseThrow(() -> new UserNotFoundException(chatMessageCreateDTO.getReceiverId()));

        ChatMessage message = chatMapper.toChatMessage(chatMessageCreateDTO);
        message.setSender(sender);
        message.setReceiver(receiver);
        message.setSentAt(LocalDateTime.now());

        chatMessageRepository.save(message);

        // Отправка через WebSocket
        messagingTemplate.convertAndSendToUser(
                receiver.getId().toString(),
                "/queue/messages",
                message
        );
    }

    @Transactional(readOnly = true)
    public List<ChatMessageResponseDTO> getChatHistory(Long currentUserId, Long otherUserId) {
        User currentUser = userRepository.findById(currentUserId).orElseThrow(() -> new UserNotFoundException(currentUserId));
        return chatMessageRepository.findChatHistory(currentUser.getId(), otherUserId).stream().map(chatMapper::toChatMessageResponseDTO).toList();
    }
}
