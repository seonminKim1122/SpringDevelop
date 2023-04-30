package com.example.springdevelop.repository;

import com.example.springdevelop.entity.Post;
import com.example.springdevelop.entity.PostLike;
import com.example.springdevelop.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface PostLikeRepository extends JpaRepository<PostLike, Long> {
    Optional<PostLike> findByPostAndUser(Post post, User user);
}
