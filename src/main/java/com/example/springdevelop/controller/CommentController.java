package com.example.springdevelop.controller;

import com.example.springdevelop.dto.CommentRequestDto;
import com.example.springdevelop.dto.CommentResponseDto;
import com.example.springdevelop.service.CommentService;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/comment")
@RequiredArgsConstructor
public class CommentController {

    private final CommentService commentService;
    @PostMapping("/{postId}")
    public CommentResponseDto writeComment(@PathVariable Long postId, @RequestBody CommentRequestDto commentRequestDto, HttpServletRequest request) {
        return commentService.writeComment(postId, commentRequestDto, request);
    }
}
