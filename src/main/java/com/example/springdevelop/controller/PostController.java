package com.example.springdevelop.controller;

import com.example.springdevelop.dto.MsgResponseDto;
import com.example.springdevelop.dto.PostDeleteRequestDto;
import com.example.springdevelop.dto.PostRequestDto;
import com.example.springdevelop.dto.PostResponseDto;
import com.example.springdevelop.service.PostService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/post")
@RequiredArgsConstructor
public class PostController {

    private final PostService postService;
    @PostMapping("/write")
    public PostResponseDto writePost(@RequestBody PostRequestDto postRequestDto) {
        return postService.writePost(postRequestDto);
    }

    @GetMapping("/list")
    public List<PostResponseDto> getAllPosts() {
        return postService.getAllPosts();
    }

    @GetMapping("/{postId}")
    public PostResponseDto getPost(@PathVariable Long postId) {
        return postService.getPost(postId);
    }

    @PutMapping("/{postId}")
    public PostResponseDto updatePost(@PathVariable Long postId, @RequestBody PostRequestDto postRequestDto) {
        return postService.updatePost(postId, postRequestDto);
    }

    @DeleteMapping("/{postId}")
    public MsgResponseDto deletePost(@PathVariable Long postId, @RequestBody PostDeleteRequestDto postDeleteRequestDto) {
        return postService.deletePost(postId, postDeleteRequestDto);
    }
}
