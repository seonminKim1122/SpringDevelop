package com.example.springdevelop.service;

import com.example.springdevelop.dto.CommentRequestDto;
import com.example.springdevelop.dto.CommentResponseDto;
import com.example.springdevelop.entity.Comment;
import com.example.springdevelop.entity.Post;
import com.example.springdevelop.entity.User;
import com.example.springdevelop.repository.CommentRepository;
import com.example.springdevelop.repository.PostRepository;
import com.example.springdevelop.repository.UserRepository;
import com.example.springdevelop.util.JwtUtil;
import io.jsonwebtoken.Claims;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class CommentService {

    private final PostRepository postRepository;
    private final CommentRepository commentRepository;
    private final UserRepository userRepository;
    private final JwtUtil jwtUtil;

    @Transactional
    public CommentResponseDto writeComment(Long postId, CommentRequestDto commentRequestDto, HttpServletRequest request) {

        Post post = postRepository.findById(postId).orElseThrow(
                () -> new NullPointerException("존재하지 않는 게시글입니다.")
        );

        Claims claims = checkTokenAndGetInfo(request);
        String username = claims.getSubject();

        User user = userRepository.findByUsername(username).orElseThrow(
                () -> new NullPointerException("가입하지 않은 username 입니다.")
        );


        Comment comment = new Comment(commentRequestDto, post, user);
        commentRepository.save(comment);

        return new CommentResponseDto(comment);
    }

    public Claims checkTokenAndGetInfo(HttpServletRequest request) {
        String jwt = jwtUtil.resolveToken(request);

        if (jwt == null || !jwtUtil.validateToken(jwt)) {
            throw new SecurityException("토큰이 유효하지 않습니다.");
        }

        return jwtUtil.getUserInfoFromToken(jwt);
    }
}
