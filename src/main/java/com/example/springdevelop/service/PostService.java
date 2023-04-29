package com.example.springdevelop.service;

import com.example.springdevelop.dto.MsgResponseDto;
import com.example.springdevelop.dto.PostRequestDto;
import com.example.springdevelop.dto.PostResponseDto;
import com.example.springdevelop.entity.Post;
import com.example.springdevelop.entity.User;
import com.example.springdevelop.repository.PostRepository;
import com.example.springdevelop.repository.UserRepository;
import com.example.springdevelop.util.JwtUtil;
import io.jsonwebtoken.Claims;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class PostService {

    private final PostRepository postRepository;
    private final UserRepository userRepository;
    private final JwtUtil jwtUtil;

    @Transactional
    public PostResponseDto writePost(PostRequestDto postRequestDto, HttpServletRequest request) {

        Claims claims = checkTokenAndGetInfo(request);

        Post post = new Post(postRequestDto);
        User user = userRepository.findByUsername(claims.getSubject()).orElseThrow(
                () -> new NullPointerException("가입하지 않은 username 입니다.")
        );
        post.setUser(user);
        postRepository.save(post);

        return new PostResponseDto(post);
    }

    @Transactional(readOnly = true)
    public List<PostResponseDto> getAllPosts() {
        List<Post> posts = postRepository.findAllByOrderByModifiedAtDesc();
        return posts.stream().map(PostResponseDto::new).collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public PostResponseDto getPost(Long postId) {
        Post post = postRepository.findById(postId).orElseThrow(
                () -> new NullPointerException("존재하지 않는 게시글입니다.")
        );
        return new PostResponseDto(post);
    }

    @Transactional
    public PostResponseDto updatePost(Long postId, PostRequestDto postRequestDto, HttpServletRequest request) {
        Post post = postRepository.findById(postId).orElseThrow(
                () -> new NullPointerException("존재하지 않는 게시글입니다.")
        );

        Claims claims = checkTokenAndGetInfo(request);
        if(!post.getUser().getUsername().equals(claims.getSubject())) {
            throw new IllegalArgumentException("직접 작성한 게시글만 수정할 수 있습니다.");
        }

        post.update(postRequestDto);
        return new PostResponseDto(post);
    }

    public MsgResponseDto deletePost(Long postId, HttpServletRequest request) {
        Post post = postRepository.findById(postId).orElseThrow(
                () -> new NullPointerException("존재하지 않는 게시글입니다.")
        );

        Claims claims = checkTokenAndGetInfo(request);
        if(!post.getUser().getUsername().equals(claims.getSubject())) {
            throw new IllegalArgumentException("직접 작성한 게시글만 삭제할 수 있습니다.");
        }

        postRepository.delete(post);
        return new MsgResponseDto("삭제 성공", HttpStatus.OK);
    }

    public Claims checkTokenAndGetInfo(HttpServletRequest request) {
        String jwt = jwtUtil.resolveToken(request);

        if (jwt == null || !jwtUtil.validateToken(jwt)) {
            throw new SecurityException("토큰이 유효하지 않습니다.");
        }

        return jwtUtil.getUserInfoFromToken(jwt);
    }
}
