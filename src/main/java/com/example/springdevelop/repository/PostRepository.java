package com.example.springdevelop.repository;

import com.example.springdevelop.dto.PostWithCommentCountDto;
import com.example.springdevelop.entity.Post;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface PostRepository extends JpaRepository<Post, Long> {
//    List<Post> findAllByOrderByModifiedAtDesc();

    @Query("select new com.example.springdevelop.dto.PostWithCommentCountDto(p, count(c)) from Post p left join Comment c on p = c.post group by p")
    Page<PostWithCommentCountDto> listOfPost(Pageable pageable);
}
