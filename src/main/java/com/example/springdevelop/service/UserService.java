package com.example.springdevelop.service;

import com.example.springdevelop.dto.MsgResponseDto;
import com.example.springdevelop.dto.UserRequestDto;
import com.example.springdevelop.entity.User;
import com.example.springdevelop.entity.UserRoleEnum;
import com.example.springdevelop.repository.UserRepository;
import com.example.springdevelop.util.JwtUtil;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final JwtUtil jwtUtil;
    private final String adminToken = "NyHiUR5IYMREDk2b81flIjvhK";

    public MsgResponseDto signup(UserRequestDto userRequestDto) {
        Optional<User> found = userRepository.findByUsername(userRequestDto.getUsername());

        if(found.isPresent()) {
            throw new IllegalArgumentException("중복된 username 입니다.");
        }

        UserRoleEnum role = UserRoleEnum.USER;
        if (userRequestDto.isAdmin()) {
            if (userRequestDto.getAdminToken().equals(adminToken)) {
                role = UserRoleEnum.ADMIN;
            } else {
                throw new IllegalArgumentException("관리자 암호가 일치하지 않습니다.");
            }
        }

        User user = new User(userRequestDto, role);
        userRepository.save(user);
        return new MsgResponseDto("회원가입 성공", HttpStatus.OK);
    }

    public MsgResponseDto login(UserRequestDto userRequestDto, HttpServletResponse response) {
        User user = userRepository.findByUsername(userRequestDto.getUsername()).orElseThrow(
                () -> new NullPointerException("가입하지 않은 username 입니다.")
        );

        if (!user.getPassword().equals(userRequestDto.getPassword())) {
            throw new IllegalArgumentException("비밀번호가 일치하지 않습니다.");
        }

        String jwt = jwtUtil.createToken(user.getUsername(), user.getRole());
        response.addHeader(JwtUtil.AUTHORIZATION_HEADER, jwt);

        return new MsgResponseDto("로그인 성공", HttpStatus.OK);
    }
}
