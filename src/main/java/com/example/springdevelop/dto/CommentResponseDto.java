package com.example.springdevelop.dto;

import com.example.springdevelop.entity.Comment;
import lombok.Getter;

import java.time.LocalDate;

@Getter
public class CommentResponseDto implements GeneralResponseDto {
    private String comment;
    private String username;
    private LocalDate modifiedAt;
    private int likes;

    public CommentResponseDto(Comment comment) {
        this.comment = comment.getContent();
        this.username = comment.getUser().getUsername();
        this.modifiedAt = comment.getModifiedAt().toLocalDate();
        this.likes = comment.getLikes();
    }
}
