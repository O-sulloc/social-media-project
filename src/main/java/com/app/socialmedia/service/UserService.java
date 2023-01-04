package com.app.socialmedia.service;

import com.app.socialmedia.domain.dto.UserDTO;
import com.app.socialmedia.domain.dto.UserJoinRequest;
import com.app.socialmedia.domain.entity.User;
import com.app.socialmedia.exception.AppException;
import com.app.socialmedia.exception.ErrorCode;
import com.app.socialmedia.repository.UserRepository;
import com.app.socialmedia.utils.JwtTokenUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class UserService {

    private final UserRepository userRepository;
    private final BCryptPasswordEncoder encoder;

    @Value("${jwt.token.secret}") //환경변수로 설정해둠
    private String secretKey;

    private Long expiredTime = 1000 * 60 * 60L; //1시간

    public String login(String userName, String password) {

        // 1. 있는 아이디로 로그인 시도하는지 체크
        User user = userRepository.findByUserName(userName)
                .orElseThrow(() -> new AppException(ErrorCode.USERNAME_NOT_FOUND, ErrorCode.USERNAME_NOT_FOUND.getMessage()));

        // 2. 비밀번호 일치하는지 체크
        if (!encoder.matches(password, user.getPassword())) {
            throw new AppException(ErrorCode.INVALID_PASSWORD, ErrorCode.INVALID_PASSWORD.getMessage());
        }

        String token = JwtTokenUtil.createToken(userName, secretKey, expiredTime);

        return token;
    }

    public UserDTO join(UserJoinRequest request) {

        //username 중복 체크
        userRepository.findByUserName(request.getUserName())
                .ifPresent(user -> {
                    throw new AppException(ErrorCode.DUPLICATED_USER_NAME, ErrorCode.DUPLICATED_USER_NAME.getMessage());
                });

        User user = userRepository.save(request.toEntity(encoder.encode(request.getPassword()))); //password encode

        return UserDTO.builder()
                .userId(user.getUserId())
                .userName(user.getUserName())
                .password(user.getPassword())
                .build();
    }
}
