package com.example.springdevelop.service;

import com.example.springdevelop.dto.MsgResponseDto;
import com.example.springdevelop.dto.UserRequestDto;
import com.example.springdevelop.entity.User;
import com.example.springdevelop.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;


    public MsgResponseDto signup(UserRequestDto userRequestDto) {
        Optional<User> found = userRepository.findByUsername(userRequestDto.getUsername());

        if(found.isPresent()) {
            throw new IllegalArgumentException("중복된 username 입니다.");
        }

        User user = new User(userRequestDto);
        userRepository.save(user);
        return new MsgResponseDto("회원가입 성공", HttpStatus.OK);
    }

    public MsgResponseDto login(UserRequestDto userRequestDto) {
        User user = userRepository.findByUsername(userRequestDto.getUsername()).orElseThrow(
                () -> new NullPointerException("가입하지 않은 username 입니다.")
        );

        if (!user.getPassword().equals(userRequestDto.getPassword())) {
            throw new IllegalArgumentException("비밀번호가 일치하지 않습니다.");
        }

        return new MsgResponseDto("로그인 성공", HttpStatus.OK);
    }
}
