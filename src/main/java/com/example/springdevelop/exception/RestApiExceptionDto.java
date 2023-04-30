package com.example.springdevelop.exception;

import lombok.Getter;
import lombok.Setter;
import org.springframework.http.HttpStatus;

@Getter
@Setter
public class RestApiExceptionDto {
    private HttpStatus httpStatus;
    private String ErrorMessage;
}
