package com.example.springdevelop.entity;

import com.example.springdevelop.dto.UserRequestDto;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity(name = "Users")
@Getter
@NoArgsConstructor
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_id")
    private Long id;

    @Column(nullable = false)
    private String username;

    @Column(nullable = false)
    private String password;

    @Column(nullable = false)
    private String role;

    public User(UserRequestDto userRequestDto, String role) {
        this.username = userRequestDto.getUsername();
        this.password = userRequestDto.getPassword();
        this.role = role;
    }
}
