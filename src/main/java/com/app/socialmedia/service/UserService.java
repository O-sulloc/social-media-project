package com.app.socialmedia.service;

import com.app.socialmedia.domain.dto.UserDTO;
import com.app.socialmedia.domain.dto.UserJoinRequest;
import com.app.socialmedia.domain.entity.User;
import com.app.socialmedia.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;

    public UserDTO join(UserJoinRequest request) {
        User user = userRepository.save(request.toEntity());

        return UserDTO.builder()
                .id(user.getId())
                .userName(user.getUserName())
                .password(user.getPassword())
                .build();
    }
}
