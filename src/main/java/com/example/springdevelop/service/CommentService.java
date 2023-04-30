package com.example.springdevelop.service;

import com.example.springdevelop.dto.CommentRequestDto;
import com.example.springdevelop.dto.CommentResponseDto;
import com.example.springdevelop.dto.GeneralResponseDto;
import com.example.springdevelop.dto.MsgResponseDto;
import com.example.springdevelop.entity.*;
import com.example.springdevelop.repository.CommentLikeRepository;
import com.example.springdevelop.repository.CommentRepository;
import com.example.springdevelop.repository.PostRepository;
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
        try {
            User user = userDetails.getUser();

            Post post = postRepository.findById(postId).orElseThrow(
                    () -> new NullPointerException("존재하지 않는 게시글입니다.")
            );

            Comment comment = new Comment(commentRequestDto, post, user);
            commentRepository.save(comment);

            return new CommentResponseDto(comment);
        } catch (Exception e) {
            return new MsgResponseDto(e.getMessage(), HttpStatus.BAD_REQUEST);
        }

    }

    @Transactional
    public GeneralResponseDto updateComment(Long commentId, CommentRequestDto commentRequestDto, UserDetailsImpl userDetails) {
        try {
            User user = userDetails.getUser();

            Comment comment = findCommentById(commentId);

            if (!comment.getUser().getUsername().equals(user.getUsername()) && !(user.getRole() == UserRoleEnum.ADMIN)) {
                throw new IllegalArgumentException("작성자만 삭제/수정할 수 있습니다.");
            }

            comment.update(commentRequestDto, user);
            return new CommentResponseDto(comment);
        } catch (Exception e) {
            return new MsgResponseDto(e.getMessage(), HttpStatus.BAD_REQUEST);
        }

    }

    @Transactional
    public MsgResponseDto deleteComment(Long commentId, UserDetailsImpl userDetails) {
        try {
            User user = userDetails.getUser();

            Comment comment = findCommentById(commentId);

            if (!comment.getUser().getUsername().equals(user.getUsername()) && !(user.getRole() == UserRoleEnum.ADMIN)) {
                throw new IllegalArgumentException("작성자만 삭제/수정할 수 있습니다.");
            }

            commentRepository.delete(comment);
            return new MsgResponseDto("삭제 완료", HttpStatus.OK);
        } catch (Exception e) {
            return new MsgResponseDto(e.getMessage(), HttpStatus.BAD_REQUEST);
        }

    }

    @Transactional
    public GeneralResponseDto likeComment(Long commentId, UserDetailsImpl userDetails) {
        try {
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
        } catch (Exception e) {
            return new MsgResponseDto(e.getMessage(), HttpStatus.BAD_REQUEST);
        }


    }

    public Comment findCommentById(Long commentId) {
        return commentRepository.findById(commentId).orElseThrow(
                () -> new NullPointerException("존재하지 않는 댓글입니다.")
        );
    }
}
