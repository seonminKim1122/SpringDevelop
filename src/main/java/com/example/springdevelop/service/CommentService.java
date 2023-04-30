package com.example.springdevelop.service;

import com.example.springdevelop.dto.CommentRequestDto;
import com.example.springdevelop.dto.CommentResponseDto;
import com.example.springdevelop.dto.GeneralResponseDto;
import com.example.springdevelop.dto.MsgResponseDto;
import com.example.springdevelop.entity.*;
import com.example.springdevelop.exception.ErrorCode;
import com.example.springdevelop.repository.CommentLikeRepository;
import com.example.springdevelop.repository.CommentRepository;
import com.example.springdevelop.repository.PostRepository;
import com.example.springdevelop.exception.CustomException;
import com.example.springdevelop.security.UserDetailsImpl;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@RequiredArgsConstructor
@Log4j2
public class CommentService {

    private final PostRepository postRepository;
    private final CommentRepository commentRepository;
    private final CommentLikeRepository commentLikeRepository;

    @Transactional
    public GeneralResponseDto writeComment(Long postId, CommentRequestDto commentRequestDto, UserDetailsImpl userDetails) {
        User user = userDetails.getUser();

        Post post = postRepository.findById(postId).orElseThrow(
                () -> new CustomException(ErrorCode.NONEXISTENT_POST)
        );

        Comment comment = new Comment(commentRequestDto, post, user);
        commentRepository.save(comment);

        return new CommentResponseDto(comment);
    }

    @Transactional
    public GeneralResponseDto updateComment(Long commentId, CommentRequestDto commentRequestDto, UserDetailsImpl userDetails) {
        User user = userDetails.getUser();

        Comment comment = findCommentById(commentId);

        if (!comment.getUser().getUsername().equals(user.getUsername()) && !(user.getRole() == UserRoleEnum.ADMIN)) {
            throw new CustomException(ErrorCode.UNAUTHORIZED_REQUEST);
        }

        comment.update(commentRequestDto, user);
        return new CommentResponseDto(comment);
    }

    @Transactional
    public MsgResponseDto deleteComment(Long commentId, UserDetailsImpl userDetails) {
        User user = userDetails.getUser();

        Comment comment = findCommentById(commentId);

        if (!comment.getUser().getUsername().equals(user.getUsername()) && !(user.getRole() == UserRoleEnum.ADMIN)) {
            throw new CustomException(ErrorCode.UNAUTHORIZED_REQUEST);
        }

        commentRepository.delete(comment);
        return new MsgResponseDto("삭제 완료", HttpStatus.OK);
    }

    @Transactional
    public GeneralResponseDto likeComment(Long commentId, UserDetailsImpl userDetails) {
        Comment comment = findCommentById(commentId);
        User user = userDetails.getUser();

        // 이미 좋아요 눌렀으면 취소
        Optional<CommentLike> found = commentLikeRepository.findByCommentAndUser(comment, user);
        if (found.isPresent()) {
            commentLikeRepository.delete(found.get());
            comment.setLikes(comment.getLikes() - 1);
            return new MsgResponseDto("좋아요 취소", HttpStatus.OK);
        }

        CommentLike commentLike = new CommentLike(comment, user);
        commentLikeRepository.save(commentLike);
        return new MsgResponseDto("좋아요", HttpStatus.OK);
    }

    public Comment findCommentById(Long commentId) {
        return commentRepository.findById(commentId).orElseThrow(
                () -> new CustomException(ErrorCode.NONEXISTENT_COMMENT)
        );
    }
}
