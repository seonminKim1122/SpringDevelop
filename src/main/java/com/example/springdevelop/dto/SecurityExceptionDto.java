package com.example.springdevelop.dto;

import lombok.Getter;

@Getter
public class SecurityExceptionDto {
    private String msg;
    private int statusCode;

    public SecurityExceptionDto(String msg, int statusCode) {
        this.msg = msg;
        this.statusCode = statusCode;
    }
}
