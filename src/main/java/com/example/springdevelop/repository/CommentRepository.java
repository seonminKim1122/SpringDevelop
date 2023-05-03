package com.example.springdevelop.repository;

import com.example.springdevelop.entity.Comment;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface CommentRepository extends JpaRepository<Comment, Long> {

    @Query("select c from Comment c where c.post.id = :postId")
    Page<Comment> listOfComment(Long postId, Pageable pageable);
}
