package com.example.springdevelop.service;

import com.example.springdevelop.dto.MsgResponseDto;
import com.example.springdevelop.dto.UserRequestDto;
import com.example.springdevelop.entity.User;
import com.example.springdevelop.entity.UserRoleEnum;
import com.example.springdevelop.exception.ErrorCode;
import com.example.springdevelop.repository.UserRepository;
import com.example.springdevelop.exception.CustomException;
import com.example.springdevelop.util.JwtUtil;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final JwtUtil jwtUtil;
    private final String adminToken = "NyHiUR5IYMREDk2b81flIjvhK";
    private final PasswordEncoder passwordEncoder;

    @Transactional
    public MsgResponseDto signup(UserRequestDto userRequestDto) {
        Optional<User> found = userRepository.findByUsername(userRequestDto.getUsername());

        if(found.isPresent()) {
            throw new CustomException(ErrorCode.ALREADY_ENROLLED);
        }

        UserRoleEnum role = UserRoleEnum.USER;
        if (userRequestDto.isAdmin()) {
            if (userRequestDto.getAdminToken().equals(adminToken)) {
                role = UserRoleEnum.ADMIN;
            } else {
                throw new CustomException(ErrorCode.WRONG_ADMIN_TOKEN);
            }
        }

        userRequestDto.setPassword(passwordEncoder.encode(userRequestDto.getPassword()));
        User user = new User(userRequestDto, role);
        userRepository.save(user);
        return new MsgResponseDto("회원가입 성공", HttpStatus.OK);
    }

    @Transactional
    public MsgResponseDto login(UserRequestDto userRequestDto, HttpServletResponse response) {
        User user = userRepository.findByUsername(userRequestDto.getUsername()).orElseThrow(
                () -> new CustomException(ErrorCode.WRONG_LOGIN_INFO)
        );

        if (!passwordEncoder.matches(userRequestDto.getPassword(), user.getPassword())) {
            throw new CustomException(ErrorCode.WRONG_LOGIN_INFO);
        }

        String jwt = jwtUtil.createToken(user.getUsername(), user.getRole());
        response.addHeader(JwtUtil.AUTHORIZATION_HEADER, jwt);

        return new MsgResponseDto("로그인 성공", HttpStatus.OK);
    }
}
