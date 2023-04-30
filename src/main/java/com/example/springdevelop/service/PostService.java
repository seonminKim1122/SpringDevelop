package com.example.springdevelop.service;

import com.example.springdevelop.dto.GeneralResponseDto;
import com.example.springdevelop.dto.MsgResponseDto;
import com.example.springdevelop.dto.PostRequestDto;
import com.example.springdevelop.dto.PostResponseDto;
import com.example.springdevelop.entity.Post;
import com.example.springdevelop.entity.PostLike;
import com.example.springdevelop.entity.User;
import com.example.springdevelop.entity.UserRoleEnum;
import com.example.springdevelop.exception.ErrorCode;
import com.example.springdevelop.repository.PostLikeRepository;
import com.example.springdevelop.repository.PostRepository;
import com.example.springdevelop.exception.CustomException;
import com.example.springdevelop.security.UserDetailsImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class PostService {

    private final PostRepository postRepository;
    private final PostLikeRepository postLikeRepository;

    @Transactional
    public GeneralResponseDto writePost(PostRequestDto postRequestDto, UserDetailsImpl userDetails) {
        User user = userDetails.getUser();

        Post post = new Post(postRequestDto);
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
    public GeneralResponseDto getPost(Long postId) {
        Post post = findPostById(postId);
        return new PostResponseDto(post);
    }

    @Transactional
    public GeneralResponseDto updatePost(Long postId, PostRequestDto postRequestDto, UserDetailsImpl userDetails) {
        User user = userDetails.getUser();

        Post post = findPostById(postId);
        if(!post.getUser().getUsername().equals(user.getUsername()) && !(user.getRole() == UserRoleEnum.ADMIN)) {
            throw new CustomException(ErrorCode.UNAUTHORIZED_REQUEST);
        }

        post.update(postRequestDto, user);
        return new PostResponseDto(post);
    }

    @Transactional
    public MsgResponseDto deletePost(Long postId, UserDetailsImpl userDetails) {
        User user = userDetails.getUser();

        Post post = findPostById(postId);

        if(!post.getUser().getUsername().equals(user.getUsername()) && !(user.getRole() == UserRoleEnum.ADMIN)) {
            throw new CustomException(ErrorCode.UNAUTHORIZED_REQUEST);
        }

        postRepository.delete(post);
        return new MsgResponseDto("삭제 성공", HttpStatus.OK);
    }

    @Transactional
    public GeneralResponseDto likePost(Long postId, UserDetailsImpl userDetails) {
        Post post = findPostById(postId);
        User user = userDetails.getUser();

        // 이미 누른 적 있으면 좋아요 취소
        Optional<PostLike> found = postLikeRepository.findByPostAndUser(post, user);
        if (found.isPresent()) {
            postLikeRepository.delete(found.get());
            post.setLikes(post.getLikes() - 1);
            return new MsgResponseDto("좋아요 취소", HttpStatus.OK);
        }

        // 누른 적 없으면 좋아요
        PostLike postLike = new PostLike(post, user);
        postLikeRepository.save(postLike);

        return new MsgResponseDto("좋아요", HttpStatus.OK);
    }

    public Post findPostById(Long postId) {
        return postRepository.findById(postId).orElseThrow(
                () -> new CustomException(ErrorCode.NONEXISTENT_POST)
        );
    }
}
