package com.app.socialmedia.controller;

import com.app.socialmedia.domain.dto.PostAddRequest;
import com.app.socialmedia.domain.dto.PostAddResponse;
import com.app.socialmedia.domain.dto.PostDTO;
import com.app.socialmedia.domain.entity.Response;
import com.app.socialmedia.service.PostService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequiredArgsConstructor
@RequestMapping("/api/v1/posts")
@Slf4j
public class PostController {

    private final PostService postService;

    @PostMapping
    public Response<PostAddResponse> addPost(@RequestBody PostAddRequest request, Authentication authentication) {
        //포스트 작성

        PostDTO postDTO = postService.addPost(request,authentication);

        log.info(authentication.getName());

        return Response.success(new PostAddResponse(postDTO.getPostId()));

    }
}
