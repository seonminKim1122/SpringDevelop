package com.example.springdevelop.repository;

import com.example.springdevelop.entity.Comment;
import com.example.springdevelop.entity.CommentLike;
import com.example.springdevelop.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface CommentLikeRepository extends JpaRepository<CommentLike, Long> {
    Optional<CommentLike> findByCommentAndUser(Comment comment, User user);
}
