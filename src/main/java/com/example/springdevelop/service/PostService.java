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
        String jwt = jwtUtil.resolveToken(request);

        if (!(jwt != null && jwtUtil.validateToken(jwt))) {
            throw new SecurityException("토큰이 유효하지 않습니다.");
        }
        Claims claims = jwtUtil.getUserInfoFromToken(jwt);

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
    public PostResponseDto updatePost(Long postId, PostRequestDto postRequestDto) {
        Post post = postRepository.findById(postId).orElseThrow(
                () -> new NullPointerException("존재하지 않는 게시글입니다.")
        );

//        if (!post.getPassword().equals(postRequestDto.getPassword())) {
//            throw new IllegalArgumentException("비밀번호가 일치하지 않습니다.");
//        }
        post.update(postRequestDto);

        return new PostResponseDto(post);
    }

    public MsgResponseDto deletePost(Long postId) {
        Post post = postRepository.findById(postId).orElseThrow(
                () -> new NullPointerException("존재하지 않는 게시글입니다.")
        );

//        if(!post.getPassword().equals(postDeleteRequestDto.getPassword())) {
//            return null;
//        }
        postRepository.delete(post);
        return null;
    }
}
