package com.example.springdevelop.controller;

import com.example.springdevelop.dto.MsgResponseDto;
import com.example.springdevelop.dto.PasswordRequestDto;
import com.example.springdevelop.dto.UserRequestDto;
import com.example.springdevelop.security.UserDetailsImpl;
import com.example.springdevelop.service.UserService;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/user")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;
    @PostMapping("/signup")
    public MsgResponseDto signup(@Valid @RequestBody UserRequestDto userRequestDto) {
        return userService.signup(userRequestDto);
    }

    @PostMapping("/login")
    public MsgResponseDto login(@RequestBody UserRequestDto userRequestDto, HttpServletResponse response) {
        return userService.login(userRequestDto, response);
    }

    @DeleteMapping("withdrawl")
    public MsgResponseDto withdrawl(@RequestBody PasswordRequestDto passwordRequestDto, @AuthenticationPrincipal UserDetailsImpl userDetails) {
        return userService.withdrawl(passwordRequestDto, userDetails);
    }
}
