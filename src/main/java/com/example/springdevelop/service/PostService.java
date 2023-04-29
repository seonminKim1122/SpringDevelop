package com.example.springdevelop.service;

import com.example.springdevelop.dto.PostRequestDto;
import com.example.springdevelop.dto.PostResponseDto;
import com.example.springdevelop.entity.Post;
import com.example.springdevelop.repository.PostRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class PostService {

    private final PostRepository postRepository;

    public PostResponseDto writePost(PostRequestDto postRequestDto) {
        Post post = new Post(postRequestDto);
        postRepository.save(post);
        return new PostResponseDto(post);
    }
}
