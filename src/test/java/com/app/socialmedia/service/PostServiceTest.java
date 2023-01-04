package com.app.socialmedia.service;

import com.app.socialmedia.domain.dto.PostAddRequest;
import com.app.socialmedia.domain.entity.User;
import com.app.socialmedia.repository.PostRepository;
import com.app.socialmedia.repository.UserRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import static org.mockito.ArgumentMatchers.any;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.test.context.support.WithMockUser;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class PostServiceTest {

    @Autowired
    private PostService postService;

    @Autowired
    private PostRepository postRepository;

    @Autowired
    private UserRepository userRepository;

    PostAddRequest request = new PostAddRequest("title test", "body test");

    //@Test
    @DisplayName("등록 성공")
    @PreAuthorize("isAuthenticated()")
    void add_success() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        User user = (User) authentication.getPrincipal();


    }

    @Test
    @DisplayName("등록 실패 - 유저가 존재하지 않을 때")
    @PreAuthorize("isAuthenticated()")
    void add_failed() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        User user = (User) authentication.getPrincipal();

        // 접속 -> 게시판 글 쓰기 시도 -> 글 다 작성하고 post로 보내는데
        // 글 등록 요청한 유저가 !!!없는 유저인 경우!!!

    }
}