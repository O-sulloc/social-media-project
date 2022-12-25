package com.app.socialmedia.controller;

import com.app.socialmedia.domain.dto.*;
import com.app.socialmedia.domain.entity.Response;
import com.app.socialmedia.service.PostService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/posts")
@Slf4j
public class PostController {

    private final PostService postService;

    @DeleteMapping("/{postId}")
    public Response<PostResponse> delete(@PathVariable Long postId, Authentication authentication) {
        postService.delete(postId, authentication);

        return Response.success(new PostResponse("포스트 삭제 완료", postId));
    }

    @PutMapping("/{postId}")
    public Response<PostResponse> update(@PathVariable Long postId, @RequestBody PostUpdateRequest request, Authentication authentication) {
        postService.update(postId, request, authentication);

        return Response.success(new PostResponse("포스트 수정 완료", postId));
    }

    @GetMapping("/{postId}")
    public Response<PostGetOneResponse> getOne(@PathVariable Long postId) {
        PostDTO postDTO = postService.getOne(postId);

        return Response.success(new PostGetOneResponse(postDTO.getPostId(), postDTO.getTitle(), postDTO.getBody(), postDTO.getUserName(), postDTO.getRegisteredAt(), postDTO.getUpdatedAt()));
    }

    @PostMapping
    public Response<PostResponse> addPost(@RequestBody PostAddRequest request, Authentication authentication) {
        //포스트 작성

        PostDTO postDTO = postService.addPost(request, authentication);

        log.info(authentication.getName());

        return Response.success(new PostResponse("포스트 등록 완료", postDTO.getPostId()));
    }
}
