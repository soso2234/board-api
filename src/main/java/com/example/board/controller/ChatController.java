package com.example.board.controller;

import com.example.board.dto.ChatMessageDto;
import com.example.board.entity.ChatMessage;
import com.example.board.repository.ChatMessageRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/chat")
public class ChatController {

    private final ChatMessageRepository chatMessageRepository;

    // 이전 채팅 기록 조회
    @GetMapping("/history")
    public List<ChatMessageDto> getChatHistory() {
        return chatMessageRepository.findTop50ByOrderByCreatedAtAsc()
                .stream()
                .map(ChatMessageDto::new)
                .collect(Collectors.toList());
    }

    // 메시지 수신 및 브로드캐스트
    @MessageMapping("/chat/send")
    @SendTo("/topic/chat")
    public ChatMessageDto sendMessage(ChatMessageDto messageDto) {
        ChatMessage message = new ChatMessage(
                messageDto.getSender(),
                messageDto.getContent(),
                messageDto.getType()
        );
        chatMessageRepository.save(message);
        return new ChatMessageDto(message);
    }

    // 입장 메시지
    @MessageMapping("/chat/enter")
    @SendTo("/topic/chat")
    public ChatMessageDto enterChat(ChatMessageDto messageDto) {
        ChatMessage message = new ChatMessage(
                messageDto.getSender(),
                messageDto.getSender() + "님이 입장하셨습니다.",
                ChatMessage.MessageType.ENTER
        );
        chatMessageRepository.save(message);
        return new ChatMessageDto(message);
    }

    // 퇴장 메시지
    @MessageMapping("/chat/leave")
    @SendTo("/topic/chat")
    public ChatMessageDto leaveChat(ChatMessageDto messageDto) {
        ChatMessage message = new ChatMessage(
                messageDto.getSender(),
                messageDto.getSender() + "님이 퇴장하셨습니다.",
                ChatMessage.MessageType.LEAVE
        );
        chatMessageRepository.save(message);
        return new ChatMessageDto(message);
    }
}