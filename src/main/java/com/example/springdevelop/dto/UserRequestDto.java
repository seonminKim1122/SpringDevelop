package com.example.springdevelop.dto;

import jakarta.validation.constraints.Pattern;
import lombok.Getter;

@Getter
public class UserRequestDto {

    // 최소 4자 이상, 10자 이하
    // 알파벳 소문자, 숫자로 구성
    @Pattern(regexp = "[a-z0-9]{4,10}")
    private String username;

    // 최소 8자 이상, 15자 이하
    // 알파벳 대소문자, 숫자로 구성
    @Pattern(regexp = "[A-Za-z0-9]{8,15}")
    private String password;
}
