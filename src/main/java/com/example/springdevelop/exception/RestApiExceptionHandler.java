package com.example.springdevelop.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class RestApiExceptionHandler {

    @ExceptionHandler(value = {CustomException.class})
    public ResponseEntity<Object> handleApiRequestException(CustomException ex) {
        RestApiExceptionDto exceptionDto = new RestApiExceptionDto();
        exceptionDto.setHttpStatus(ex.getErrorCode().getHttpStatus());
        exceptionDto.setErrorMessage(ex.getErrorCode().getErrorMessage());

        return new ResponseEntity(
                exceptionDto,
                ex.getErrorCode().getHttpStatus()
        );
    }

    // 회원가입 시 아이디, 비밀번호 validation
    @ExceptionHandler(value = {MethodArgumentNotValidException.class})
    public String validationException(MethodArgumentNotValidException ex) {
        BindingResult bindingResult = ex.getBindingResult();
        StringBuilder sb = new StringBuilder();
        for(FieldError fieldError : bindingResult.getFieldErrors()) {
            sb.append(fieldError.getField() + " : ");
            sb.append(fieldError.getDefaultMessage() + "\n");
        }
        sb.setLength(sb.length()-1);

        return sb.toString();
    }
}
