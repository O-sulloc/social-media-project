package com.app.socialmedia.controller;

import com.app.socialmedia.domain.dto.UserLoginRequest;
import com.app.socialmedia.domain.dto.UserDTO;
import com.app.socialmedia.domain.dto.UserJoinRequest;
import com.app.socialmedia.service.UserService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest
class UserControllerTest {

    @MockBean
    UserService userService;

    @Autowired
    ObjectMapper objectMapper;

    @Autowired
    private MockMvc mockMvc;

    UserJoinRequest request = new UserJoinRequest("kjh", "q1w2e3r4t5");

    //@Test
    @DisplayName("로그인 실패 -password 틀림")
    void login_failed_2() {
    }

    //@Test
    @DisplayName("로그인 실패 -userName 없음")
    void login_failed_1() {
    }

    //@Test
    @DisplayName("로그인 성공")
    void login_success() throws Exception {
        String userName = "jkh";
        String password = "1q2w3e4r5t";

        when(userService.login(any(), any()))
                .thenReturn("token");

        mockMvc.perform(post("/api/v1/users/login")
                        .with(csrf())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsBytes(new UserLoginRequest(userName, password))))
                .andDo(print())
                .andExpect(status().isOk());
    }


    //@Test
    @DisplayName("회원가입 실패 - userName 중복")
    void join_failed() {
    }

    //@Test
    @DisplayName("회원가입 성공")
    void join() throws Exception {
        when(userService.join(any())).thenReturn(mock(UserDTO.class));

        mockMvc.perform(post("/api/v1/users/join")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsBytes(request)))
                .andDo(print())
                .andExpect(status().isOk());
    }
}