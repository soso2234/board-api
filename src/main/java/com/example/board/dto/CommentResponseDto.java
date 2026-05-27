package com.example.board.dto;

import com.example.board.entity.Comment;
import lombok.Getter;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Getter
public class CommentResponseDto {

    private Long id;
    private String content;
    private String author;
    private LocalDateTime createdAt;
    private Long parentId;
    private List<CommentResponseDto> children;

    public CommentResponseDto(Comment comment) {
        this.id = comment.getId();
        this.content = comment.getContent();
        this.author = comment.getAuthor();
        this.createdAt = comment.getCreatedAt();
        this.parentId = comment.getParent() != null ? comment.getParent().getId() : null;
        this.children = comment.getChildren().stream()
                .map(CommentResponseDto::new)
                .collect(Collectors.toList());
    }
}