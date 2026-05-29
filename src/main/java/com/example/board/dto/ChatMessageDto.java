package com.example.board.dto;

import com.example.board.entity.ChatMessage;
import com.example.board.entity.ChatMessage.MessageType;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Getter
@NoArgsConstructor
public class ChatMessageDto {

    private String sender;
    private String content;
    private MessageType type;
    private LocalDateTime createdAt;

    // 요청용 생성자
    public ChatMessageDto(String sender, String content, MessageType type) {
        this.sender = sender;
        this.content = content;
        this.type = type;
    }

    // Entity → DTO 변환
    public ChatMessageDto(ChatMessage message) {
        this.sender = message.getSender();
        this.content = message.getContent();
        this.type = message.getType();
        this.createdAt = message.getCreatedAt();
    }
}