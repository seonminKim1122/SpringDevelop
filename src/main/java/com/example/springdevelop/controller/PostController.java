package com.example.springdevelop.controller;

import com.example.springdevelop.dto.PostRequestDto;
import com.example.springdevelop.dto.PostResponseDto;
import com.example.springdevelop.service.PostService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/post")
@RequiredArgsConstructor
public class PostController {

    private final PostService postService;
    @PostMapping("/write")
    public PostResponseDto writePost(@RequestBody PostRequestDto postRequestDto) {
        return postService.writePost(postRequestDto);
    }
}
