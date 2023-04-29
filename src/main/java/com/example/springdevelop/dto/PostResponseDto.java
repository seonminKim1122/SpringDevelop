package com.example.springdevelop.dto;

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
    private List<CommentResponseDto> comments;

    public PostResponseDto(Post post) {
        this.title = post.getTitle();
        this.username = post.getUser().getUsername();
        this.modifiedAt = post.getModifiedAt().toLocalDate();
        this.content = post.getContent();
        this.comments = post.getComments().stream().sorted((c1, c2) -> c2.getModifiedAt().compareTo(c1.getModifiedAt())).map(CommentResponseDto::new).collect(Collectors.toList());
    }
}
