package com.example.springdevelop.dto;

import com.example.springdevelop.entity.Comment;
import lombok.Getter;

import java.time.LocalDate;

@Getter
public class CommentResponseDto {
    private String comment;
    private String username;
    private LocalDate modifiedAt;

    public CommentResponseDto(Comment comment) {
        this.comment = comment.getContent();
        this.username = comment.getUser().getUsername();
        this.modifiedAt = comment.getModifiedAt().toLocalDate();
    }
}
