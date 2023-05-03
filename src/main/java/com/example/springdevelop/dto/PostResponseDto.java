package com.example.springdevelop.dto;

import com.example.springdevelop.entity.Comment;
import com.example.springdevelop.entity.Post;
import lombok.Getter;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@Getter
public class PostResponseDto implements GeneralResponseDto {
    private String title;
    private String username;
    private LocalDate modifiedAt;
    private String content;
    private int likes;
    private List<CommentResponseDto> comments;

    public PostResponseDto(Post post, List<Comment> comments) {
        this.title = post.getTitle();
        this.username = post.getUser().getUsername();
        this.modifiedAt = post.getModifiedAt().toLocalDate();
        this.content = post.getContent();
        this.likes = post.getLikes();
        this.comments = comments.stream().map(CommentResponseDto::new).collect(Collectors.toList());
    }

    public PostResponseDto(Post post) {
        this.title = post.getTitle();
        this.username = post.getUser().getUsername();
        this.modifiedAt = post.getModifiedAt().toLocalDate();
        this.content = post.getContent();
        this.likes = post.getLikes();
        this.comments = post.getComments().stream().sorted((p1, p2) -> p2.getModifiedAt().compareTo(p1.getModifiedAt())).map(CommentResponseDto::new).collect(Collectors.toList());
    }
}
