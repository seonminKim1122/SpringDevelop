package com.example.springdevelop.entity;

import com.example.springdevelop.dto.CommentRequestDto;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@NoArgsConstructor
public class Comment extends TimeStamped {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "comment_id")
    private Long id;

    @Column(nullable = false)
    private String content;

    @Column(nullable = false)
    private int likes = 0;

    @ManyToOne
    @JoinColumn(name = "post_id")
    @OnDelete(action = OnDeleteAction.CASCADE)
    private Post post;

    @ManyToOne
    @JoinColumn(name = "user_id")
    @OnDelete(action = OnDeleteAction.CASCADE)
    private User user;

    @OneToMany(mappedBy = "comment")
    private List<CommentLike> commentLikes = new ArrayList<>();

    public Comment(CommentRequestDto commentRequestDto, Post post, User user) {
        this.content = commentRequestDto.getComment();
        this.user = user;
        this.post = post;
        post.getComments().add(this);
    }

    public void update(CommentRequestDto commentRequestDto, User user) {
        this.content = commentRequestDto.getComment();
        this.user = user;
    }

    public void setLikes(int likes) {
        this.likes = likes;
    }
}
