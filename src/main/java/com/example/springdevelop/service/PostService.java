package com.example.springdevelop.service;

import com.example.springdevelop.dto.GeneralResponseDto;
import com.example.springdevelop.dto.MsgResponseDto;
import com.example.springdevelop.dto.PostRequestDto;
import com.example.springdevelop.dto.PostResponseDto;
import com.example.springdevelop.entity.Post;
import com.example.springdevelop.entity.User;
import com.example.springdevelop.entity.UserRoleEnum;
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
    public GeneralResponseDto writePost(PostRequestDto postRequestDto, HttpServletRequest request) {
        try {
            Claims claims = checkTokenAndGetInfo(request);
            String username = claims.getSubject();
            User user = findUserByUsername(username);

            Post post = new Post(postRequestDto);
            post.setUser(user);
            postRepository.save(post);

            return new PostResponseDto(post);
        } catch (Exception e) {
            return new MsgResponseDto(e.getMessage(), HttpStatus.BAD_REQUEST);
        }

    }

    @Transactional(readOnly = true)
    public List<PostResponseDto> getAllPosts() {
        List<Post> posts = postRepository.findAllByOrderByModifiedAtDesc();
        return posts.stream().map(PostResponseDto::new).collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public GeneralResponseDto getPost(Long postId) {
        try {
            Post post = findPostById(postId);
            return new PostResponseDto(post);
        } catch (Exception e) {
            return new MsgResponseDto(e.getMessage(), HttpStatus.BAD_REQUEST);
        }

    }

    @Transactional
    public GeneralResponseDto updatePost(Long postId, PostRequestDto postRequestDto, HttpServletRequest request) {
        try {
            Claims claims = checkTokenAndGetInfo(request);
            String username = claims.getSubject();
            User user = findUserByUsername(username);

            Post post = findPostById(postId);
            if(!post.getUser().getUsername().equals(username) && !(user.getRole() == UserRoleEnum.ADMIN)) {
                throw new IllegalArgumentException("작성자만 삭제/수정할 수 있습니다.");
            }

            post.update(postRequestDto, user);
            return new PostResponseDto(post);
        } catch (Exception e) {
            return new MsgResponseDto(e.getMessage(), HttpStatus.BAD_REQUEST);
        }

    }

    public MsgResponseDto deletePost(Long postId, HttpServletRequest request) {
        try {
            Claims claims = checkTokenAndGetInfo(request);
            String username = claims.getSubject();
            User user = findUserByUsername(username);

            Post post = findPostById(postId);

            if(!post.getUser().getUsername().equals(username) && !(user.getRole() == UserRoleEnum.ADMIN)) {
                throw new IllegalArgumentException("작성자만 삭제/수정할 수 있습니다.");
            }

            postRepository.delete(post);
            return new MsgResponseDto("삭제 성공", HttpStatus.OK);
        } catch (Exception e) {
            return new MsgResponseDto(e.getMessage(), HttpStatus.BAD_REQUEST);
        }

    }

    public Claims checkTokenAndGetInfo(HttpServletRequest request) {
        String jwt = jwtUtil.resolveToken(request);

        if (jwt == null || !jwtUtil.validateToken(jwt)) {
            throw new SecurityException("토큰이 유효하지 않습니다.");
        }

        return jwtUtil.getUserInfoFromToken(jwt);
    }

    public Post findPostById(Long postId) {
        return postRepository.findById(postId).orElseThrow(
                () -> new NullPointerException("존재하지 않는 게시글입니다.")
        );
    }

    public User findUserByUsername(String username) {
        return userRepository.findByUsername(username).orElseThrow(
                () -> new NullPointerException("회원을 찾을 수 없습니다.")
        );
    }
}
