package com.example.dating_app.mapper;

import com.example.dating_app.dto.ChatMessageCreateDTO;
import com.example.dating_app.dto.ChatMessageResponseDTO;
import com.example.dating_app.model.ChatMessage;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface ChatMapper {
    ChatMessage toChatMessage(ChatMessageCreateDTO chatMessageCreateDTO);
    ChatMessageResponseDTO toChatMessageResponseDTO(ChatMessage chatMessage);
}
