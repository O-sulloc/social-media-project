package com.app.socialmedia.controller;

import com.app.socialmedia.domain.dto.comment.*;
import com.app.socialmedia.domain.dto.myFeed.MyFeedInfoResponse;
import com.app.socialmedia.domain.dto.post.*;
import com.app.socialmedia.domain.entity.Response;
import com.app.socialmedia.service.CommentService;
import com.app.socialmedia.service.LikeService;
import com.app.socialmedia.service.PostService;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/posts")
@Slf4j
public class PostController {

    private final PostService postService;
    private final CommentService commentService;
    private final LikeService likeService;


    @ApiOperation(value = "마이 피드", notes = "특정 사용자가 작성한 모든 게시글을 조회합니다. 로그인한 사용자만 가능합니다.")
    @GetMapping("/my")
    public Response<MyFeedInfoResponse> getMyPosts(@PageableDefault(size = 20, sort = "registeredAt", direction = Sort.Direction.DESC) Pageable pageable,
                                                   Authentication authentication) {
        MyFeedInfoResponse postResponse = postService.getMyFeed(pageable, authentication);

        return Response.success(postResponse);
    }

    @ApiOperation(value = "좋아요 조회", notes = "특정 게시글의 좋아요 개수를 조회합니다.")
    @GetMapping("/{postId}/likes")
    public Response<Long> getLike(@PathVariable Long postId) {
        log.info("컨트롤러 요청들어옴");

        Long count = likeService.getLike(postId);

        return Response.success(count);
    }


    @ApiOperation(value = "좋아요 누르기", notes = "특정 게시글에 좋아요를 누릅니다. 로그인한 사용자만 가능합니다.")
    @PostMapping("/{postId}/likes")
    public Response<String> addLike(@PathVariable Long postId, Authentication authentication) {
        if (!likeService.addLike(authentication, postId)) {
            return Response.success("좋아요 취소");
        }

        return Response.success("좋아요를 눌렀습니다.");

    }


    @ApiOperation(value = "댓글 조회", notes = "특정 게시글의 댓글을 전체 조회합니다.")
    @GetMapping("/{postId}/comments")
    public Response<CommentInfoResponse> getAllComments(@PathVariable Long postId, @PageableDefault(size = 10, sort = "registeredAt", direction = Sort.Direction.DESC) Pageable pageable) {

        CommentInfoResponse commentInfoResponse = commentService.getAllComments(pageable, postId);

        return Response.success(commentInfoResponse);
    }


    @ApiOperation(value = "댓글 수정", notes = "특정 게시글에 달린 댓글을 수정합니다. 작성자만 수정할 수 있습니다.")
    @PutMapping("/{postId}/comments/{id}")
    public Response<CommentUpdateResponse> updateComment(@PathVariable Long postId, @PathVariable Long id, @RequestBody CommentRequest request, Authentication authentication) {

        CommentDTO commentDTO = commentService.updateComment(id, request, authentication);

        return Response.success(new CommentUpdateResponse(commentDTO.getId(), commentDTO.getComment(), commentDTO.getUser().getUserName(), commentDTO.getPost().getPostId(), commentDTO.getUpdatedAt()));
    }


    @ApiOperation(value = "댓글 삭제", notes = "특정 게시글에 달린 댓글을 삭제합니다. 작성자만 삭제할 수 있습니다.")
    @DeleteMapping("/{postId}/comments/{id}")
    public Response<CommentDeleteResponse> deleteComment(@PathVariable Long postId, @PathVariable Long id, Authentication authentication) {
        commentService.deleteComment(id, authentication);

        return Response.success(new CommentDeleteResponse("댓글 삭제 완료", id));
    }


    @ApiOperation(value = "댓글 작성", notes = "특정 게시글에 댓글을 작성합니다. 로그인한 사용자만 작성 가능합니다.")
    @PostMapping("/{postId}/comments")
    public Response<CommentResponse> addComment(@PathVariable Long postId, @RequestBody CommentRequest request, Authentication authentication) {

        CommentDTO commentDTO = commentService.addComment(request, postId, authentication);

        return Response.success(new CommentResponse(commentDTO.getId(), commentDTO.getComment(), commentDTO.getUser().getUserName(), commentDTO.getPost().getPostId(), commentDTO.getRegisteredAt()));
    }

    @ApiOperation(value = "게시글 전체 조회", notes = "등록된 모든 게시글을 조회합니다.")
    @GetMapping
    public Response<PageInfoResponse> getList(@PageableDefault(size = 20, sort = "registeredAt", direction = Sort.Direction.DESC) Pageable pageable) {
        PageInfoResponse postResponses = postService.getList(pageable);

        return Response.success(postResponses);
    }


    @ApiOperation(value = "게시글 삭제", notes = "게시글을 삭제합니다. 작성자만 삭제할 수 있습니다.")
    @DeleteMapping("/{postId}")
    public Response<PostResponse> delete(@PathVariable Long postId, Authentication authentication) {
        postService.delete(postId, authentication);

        return Response.success(new PostResponse("포스트 삭제 완료", postId));
    }

    @ApiOperation(value = "게시글 수정", notes = "게시글을 수정합니다. 작성자만 수정할 수 있습니다.")
    @PutMapping("/{postId}")
    public Response<PostResponse> update(@PathVariable Long postId, @RequestBody PostUpdateRequest request, Authentication authentication) {
        postService.update(postId, request, authentication);

        return Response.success(new PostResponse("포스트 수정 완료", postId));
    }

    @ApiOperation(value = "게시글 단건 조회", notes = "한 개의 게시글만 조회합니다.")
    @GetMapping("/{postId}")
    public Response<PostGetOneResponse> getOne(@PathVariable Long postId) {
        PostDTO postDTO = postService.getOne(postId);

        return Response.success(new PostGetOneResponse(postDTO.getPostId(), postDTO.getTitle(), postDTO.getBody(), postDTO.getUserName(), postDTO.getRegisteredAt(), postDTO.getUpdatedAt()));
    }

    @ApiOperation(value = "게시글 등록", notes = "게시글을 작성합니다. 로그인한 사용자만 작성 가능합니다.")
    @PostMapping
    public Response<PostResponse> addPost(@RequestBody PostAddRequest request, Authentication authentication) {
        //포스트 작성

        PostDTO postDTO = postService.addPost(request, authentication);

        log.info(authentication.getName());

        return Response.success(new PostResponse("포스트 등록 완료", postDTO.getPostId()));
    }
}

