package com.example.springdevelop.controller;

import com.example.springdevelop.dto.MsgResponseDto;
import com.example.springdevelop.dto.UserRequestDto;
import com.example.springdevelop.service.UserService;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/user")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;
    @PostMapping("/signup")
    public MsgResponseDto signup(@RequestBody UserRequestDto userRequestDto) {
        return userService.signup(userRequestDto);
    }

    @PostMapping("/login")
    public MsgResponseDto login(@RequestBody UserRequestDto userRequestDto, HttpServletResponse response) {
        return userService.login(userRequestDto, response);
    }
}
