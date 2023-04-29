package com.example.springdevelop.dto;

import lombok.Getter;

@Getter
public class MsgResponseDto {
    private String msg;

    public MsgResponseDto(String msg) {
        this.msg = msg;
    }
}
