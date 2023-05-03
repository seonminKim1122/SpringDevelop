package com.example.springdevelop.dto;

import com.example.springdevelop.entity.Post;
import lombok.Getter;

import java.time.LocalDate;

@Getter
public class PostWithCommentCountDto {
    private String title;
    private String username;
    private LocalDate modifiedAt;
    private String content;
    private int likes;
    private long commentCount;

    public PostWithCommentCountDto(Post post, Long commentCount) {
        this.title = post.getTitle();
        this.username = post.getUser().getUsername();
        this.modifiedAt = post.getModifiedAt().toLocalDate();
        this.content = post.getContent();
        this.likes = post.getLikes();
        this.commentCount = commentCount;
    }
}
