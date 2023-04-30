package com.example.springdevelop.exception;

import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public enum ErrorCode {
    // 400 - BAD REQUEST
    ALREADY_ENROLLED(HttpStatus.BAD_REQUEST, "중복된 username 입니다."),
    WRONG_ADMIN_TOKEN(HttpStatus.BAD_REQUEST, "관리자 암호가 일치하지 않습니다."),
    WRONG_LOGIN_INFO(HttpStatus.BAD_REQUEST, "회원을 찾을 수 없습니다."),
    NONEXISTENT_POST(HttpStatus.BAD_REQUEST, "존재하지 않는 게시글입니다."),
    NONEXISTENT_COMMENT(HttpStatus.BAD_REQUEST, "존재하지 않는 댓글입니다."),
    // 403 - FORBIDDEN
    UNAUTHORIZED_REQUEST(HttpStatus.FORBIDDEN, "작성자만 삭제/수정할 수 있습니다.");


    private final HttpStatus httpStatus;
    private final String errorMessage;

    ErrorCode(HttpStatus httpStatus, String errorMessage) {
        this.httpStatus = httpStatus;
        this.errorMessage = errorMessage;
    }
}
