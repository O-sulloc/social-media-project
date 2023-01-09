package com.app.socialmedia.controller;

import com.app.socialmedia.domain.dto.comment.*;
import com.app.socialmedia.domain.dto.myFeed.MyFeedInfoResponse;
import com.app.socialmedia.domain.dto.post.*;
import com.app.socialmedia.domain.entity.Response;
import com.app.socialmedia.service.CommentService;
import com.app.socialmedia.service.LikeService;
import com.app.socialmedia.service.PostService;
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


    @GetMapping("/my")
    public Response<MyFeedInfoResponse> getMyPosts(@PageableDefault(size = 20, sort = "registeredAt", direction = Sort.Direction.DESC) Pageable pageable,
                                                   Authentication authentication) {
        MyFeedInfoResponse postResponse = postService.getMyFeed(pageable, authentication);

        return Response.success(postResponse);
    }

    /**
     * 좋아요 개수 조회
     *
     * @param postId (조회할 포스트)
     * @return 좋아요 개수
     */
    @GetMapping("/{postId}/likes")
    public Response<Long> getLike(@PathVariable Long postId) {
        log.info("컨트롤러 요청들어옴");

        Long count = likeService.getLike(postId);

        return Response.success(count);
    }


    /**
     * 좋아요 기능
     *
     * @param postId
     * @param authentication
     * @return
     */
    @PostMapping("/{postId}/likes")
    public Response<String> addLike(@PathVariable Long postId, Authentication authentication) {
        if (!likeService.addLike(authentication, postId)) {
            return Response.success("좋아요 취소");
        }

        return Response.success("좋아요를 눌렀습니다.");

    }


    /**
     * 댓글 조회
     *
     * @param postId   (포스트 번호)
     * @param pageable
     * @return commentInfoResponse
     */
    @GetMapping("/{postId}/comments")
    public Response<CommentInfoResponse> getAllComments(@PathVariable Long postId, @PageableDefault(size = 10, sort = "registeredAt", direction = Sort.Direction.DESC) Pageable pageable) {

        CommentInfoResponse commentInfoResponse = commentService.getAllComments(pageable, postId);

        return Response.success(commentInfoResponse);
    }


    /**
     * 댓글 수정
     *
     * @param postId         (포스트 번호)
     * @param id             (댓글 번호)
     * @param authentication
     * @return id, comment, userName, postId, lastModifiedAt
     */
    @PutMapping("/{postId}/comments/{id}")
    public Response<CommentUpdateResponse> updateComment(@PathVariable Long postId, @PathVariable Long id, @RequestBody CommentRequest request, Authentication authentication) {

        CommentDTO commentDTO = commentService.updateComment(id, request, authentication);

        return Response.success(new CommentUpdateResponse(commentDTO.getId(), commentDTO.getComment(), commentDTO.getUser().getUserName(), commentDTO.getPost().getPostId(), commentDTO.getUpdatedAt()));
    }


    /**
     * 댓글 삭제
     *
     * @param postId         (포스트 번호)
     * @param id             (댓글 번호)
     * @param authentication
     * @return message, id
     */
    @DeleteMapping("/{postId}/comments/{id}")
    public Response<CommentDeleteResponse> deleteComment(@PathVariable Long postId, @PathVariable Long id, Authentication authentication) {
        commentService.deleteComment(id, authentication);

        return Response.success(new CommentDeleteResponse("댓글 삭제 완료", id));
    }


    /**
     * 댓글 작성
     *
     * @param postId         (포스트 번호)
     * @param request        (comment)
     * @param authentication
     * @return id, comment, userName, postId, createdAt
     */
    @PostMapping("/{postId}/comments")
    public Response<CommentResponse> addComment(@PathVariable Long postId, @RequestBody CommentRequest request, Authentication authentication) {

        CommentDTO commentDTO = commentService.addComment(request, postId, authentication);

        return Response.success(new CommentResponse(commentDTO.getId(), commentDTO.getComment(), commentDTO.getUser().getUserName(), commentDTO.getPost().getPostId(), commentDTO.getRegisteredAt()));
    }

    @GetMapping
    public Response<PageInfoResponse> getList(@PageableDefault(size = 20, sort = "registeredAt", direction = Sort.Direction.DESC) Pageable pageable) {
        PageInfoResponse postResponses = postService.getList(pageable);

        return Response.success(postResponses);
    }


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

