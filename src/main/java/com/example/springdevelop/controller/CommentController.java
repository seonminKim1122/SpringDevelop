package com.example.springdevelop.controller;

import com.example.springdevelop.dto.CommentRequestDto;
import com.example.springdevelop.dto.GeneralResponseDto;
import com.example.springdevelop.dto.MsgResponseDto;
import com.example.springdevelop.security.UserDetailsImpl;
import com.example.springdevelop.service.CommentService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/comment")
@RequiredArgsConstructor
public class CommentController {

    private final CommentService commentService;
    @PostMapping("/{postId}")
    public GeneralResponseDto writeComment(@PathVariable Long postId, @RequestBody CommentRequestDto commentRequestDto, @AuthenticationPrincipal UserDetailsImpl userDetails) {
        return commentService.writeComment(postId, commentRequestDto, userDetails);
    }

    @PutMapping("/{commentId}")
    public GeneralResponseDto updateComment(@PathVariable Long commentId, @RequestBody CommentRequestDto commentRequestDto, @AuthenticationPrincipal UserDetailsImpl userDetails) {
        return commentService.updateComment(commentId, commentRequestDto, userDetails);
    }

    @DeleteMapping("/{commentId}")
    public MsgResponseDto deleteComment(@PathVariable Long commentId, @AuthenticationPrincipal UserDetailsImpl userDetails) {
        return commentService.deleteComment(commentId, userDetails);
    }
}
