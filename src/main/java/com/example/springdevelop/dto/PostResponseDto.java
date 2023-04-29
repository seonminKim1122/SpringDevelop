package com.example.springdevelop.dto;

import com.example.springdevelop.entity.Post;
import lombok.Getter;

import java.time.LocalDate;

@Getter
public class PostResponseDto {
    private String title;
    private String username;
    private LocalDate modifiedAt;
    private String content;

    public PostResponseDto(Post post) {
        this.title = post.getTitle();
        this.username = post.getUser().getUsername();
        this.modifiedAt = post.getModifiedAt().toLocalDate();
        this.content = post.getContent();
    }
}
