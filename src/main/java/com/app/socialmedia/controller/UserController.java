package com.app.socialmedia.controller;

import com.app.socialmedia.domain.dto.UserDTO;
import com.app.socialmedia.domain.dto.UserJoinRequest;
import com.app.socialmedia.domain.dto.UserJoinResponse;
import com.app.socialmedia.domain.entity.Response;
import com.app.socialmedia.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/users")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @PostMapping("/join")
    public Response<UserJoinResponse> join(@RequestBody UserJoinRequest request) {
        // 가입할 userName, password가 입력 -> service로 요청 보냄 -> user 클래스 만들어짐

        UserDTO userDTO = userService.join(request);

        return Response.success(new UserJoinResponse(userDTO.getId(), userDTO.getUserName()));
    }
}
