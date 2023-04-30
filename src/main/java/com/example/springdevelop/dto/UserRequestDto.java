package com.example.springdevelop.dto;

import jakarta.validation.constraints.Pattern;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserRequestDto {

    // 최소 4자 이상, 10자 이하
    // 알파벳 소문자, 숫자로 구성
    @Pattern(regexp = "[a-z0-9]{4,10}", message = "4~10 자의 알파벳 소문자와 숫자로 구성되어야 합니다.")
    private String username;

    // 최소 8자 이상, 15자 이하
    // 알파벳 대소문자, 숫자로 구성
    @Pattern(regexp = "[A-Za-z0-9]{8,15}", message = "8~15 자의 알파벳 대소문자, 숫자로 구성되어야 합니다.")
    private String password;

    private boolean admin = false;

    private String adminToken;
}
