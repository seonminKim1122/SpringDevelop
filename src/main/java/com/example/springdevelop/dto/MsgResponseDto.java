package com.example.springdevelop.dto;

import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public class MsgResponseDto {
    private String msg;
    private HttpStatus statusCode;

    public MsgResponseDto(String msg, HttpStatus httpStatus) {
        this.msg = msg;
        this.statusCode = httpStatus;
    }
}
