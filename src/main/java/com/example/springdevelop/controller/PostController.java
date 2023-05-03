package com.example.springdevelop.controller;

import com.example.springdevelop.dto.GeneralResponseDto;
import com.example.springdevelop.dto.PageRequestDto;
import com.example.springdevelop.dto.PostRequestDto;
import com.example.springdevelop.dto.PostWithCommentCountDto;
import com.example.springdevelop.security.UserDetailsImpl;
import com.example.springdevelop.service.PostService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/post")
@RequiredArgsConstructor
public class PostController {

    private final PostService postService;

    @PostMapping("/write")
    public GeneralResponseDto writePost(@RequestBody PostRequestDto postRequestDto, @AuthenticationPrincipal UserDetailsImpl userDetails) {
        return postService.writePost(postRequestDto, userDetails);
    }

    @GetMapping("/search/list")
    public List<PostWithCommentCountDto> getAllPosts(@ModelAttribute PageRequestDto pageRequestDto) {
        return postService.getAllPosts(pageRequestDto);
    }

    @GetMapping("/search/{postId}")
    public GeneralResponseDto getPost(@PathVariable Long postId, @ModelAttribute PageRequestDto pageRequestDto) {
        return postService.getPost(postId, pageRequestDto);
    }

    @PutMapping("/{postId}")
    public GeneralResponseDto updatePost(@PathVariable Long postId, @RequestBody PostRequestDto postRequestDto, @AuthenticationPrincipal UserDetailsImpl userDetails) {
        return postService.updatePost(postId, postRequestDto, userDetails);
    }

    @DeleteMapping("/{postId}")
    public GeneralResponseDto deletePost(@PathVariable Long postId, @AuthenticationPrincipal UserDetailsImpl userDetails) {
        return postService.deletePost(postId, userDetails);
    }

    @PostMapping("/like/{postId}")
    public GeneralResponseDto likePost(@PathVariable Long postId, @AuthenticationPrincipal UserDetailsImpl userDetails) {
        return postService.likePost(postId, userDetails);
    }
}
