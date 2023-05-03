package com.example.springdevelop.dto;

import com.example.springdevelop.entity.Post;
import lombok.Getter;

@Getter
public class PostWithCommentCountDto extends PostResponseDto {

    private long commentCount;

    public PostWithCommentCountDto(Post post, Long commentCount) {
        super(post);
        this.commentCount = commentCount;
    }
}
