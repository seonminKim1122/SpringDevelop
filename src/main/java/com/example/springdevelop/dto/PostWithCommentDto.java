package com.example.springdevelop.dto;

import com.example.springdevelop.entity.Comment;
import com.example.springdevelop.entity.Post;
import lombok.Getter;

import java.util.List;
import java.util.stream.Collectors;

@Getter
public class PostWithCommentDto extends PostResponseDto {

    private List<CommentResponseDto> comments;
    private PageResponseDto<CommentResponseDto> pageResponseDto;
    public PostWithCommentDto(Post post) {
        super(post);
        this.comments = post.getComments().stream().sorted((p1, p2) -> p2.getModifiedAt().compareTo(p1.getModifiedAt())).map(CommentResponseDto::new).collect(Collectors.toList());
    }

    public PostWithCommentDto(Post post, PageRequestDto pageRequestDto, List<Comment> comments, int total) {
        super(post);
        this.pageResponseDto = new PageResponseDto<>(pageRequestDto, comments.stream().map(CommentResponseDto::new).collect(Collectors.toList()), total);
    }
}
