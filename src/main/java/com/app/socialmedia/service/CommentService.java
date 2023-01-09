package com.app.socialmedia.service;

import com.app.socialmedia.domain.dto.alarm.AlarmType;
import com.app.socialmedia.domain.dto.comment.*;
import com.app.socialmedia.domain.entity.Alarm;
import com.app.socialmedia.domain.entity.Comment;
import com.app.socialmedia.domain.entity.Post;
import com.app.socialmedia.domain.entity.User;
import com.app.socialmedia.exception.AppException;
import com.app.socialmedia.exception.ErrorCode;
import com.app.socialmedia.repository.AlarmRepository;
import com.app.socialmedia.repository.CommentRepository;
import com.app.socialmedia.repository.PostRepository;
import com.app.socialmedia.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Slf4j
public class CommentService {

    private final UserRepository userRepository;
    private final CommentRepository commentRepository;
    private final PostRepository postRepository;
    private final AlarmRepository alarmRepository;


    /*--- 댓글 전체 조회---*/
    public CommentInfoResponse getAllComments(Pageable pageable, Long postId) {

        // 1. 포스트 존재 여부 검증
        Post post = postRepository.findById(postId)
                .orElseThrow(() -> new AppException(ErrorCode.POST_NOT_FOUND, ErrorCode.POST_NOT_FOUND.getMessage()));

        log.info("post잘 가져오는지 postId:{}", post.getPostId());

        // 2. 댓글 찾기
        //Page<Comment> commentList = commentRepository.findAll(pageable);
        Page<Comment> commentList = commentRepository.findByPost(post, pageable);

        log.info("댓글:{}", commentList);

        Page<CommentResponse> commentResponse = commentList.map(
                comment -> CommentResponse.builder()
                        .id(comment.getId())
                        .comment(comment.getComment())
                        .userName(comment.getUser().getUserName())
                        .postId(comment.getPost().getPostId())
                        .createdAt(comment.getRegisteredAt())
                        .build()
        );

        CommentInfoResponse commentInfoResponse = CommentInfoResponse.builder()
                .content(commentResponse.getContent())
                .pageable(commentResponse.getPageable())
                .last(commentResponse.hasNext())
                .totalPages(commentResponse.getTotalPages())
                .size(commentResponse.getSize())
                .number(commentResponse.getNumber())
                .sort(commentResponse.getSort())
                .first(commentResponse.isFirst())
                .numberOfElements(commentResponse.getNumberOfElements())
                .empty(commentResponse.isEmpty())
                .build();

        return commentInfoResponse;
    }


    /*--- 댓글 수정 ---*/
    @Transactional
    public CommentDTO updateComment(Long id, CommentRequest request, Authentication authentication) {

        // TODO: admin 권한

        // 1. 댓글 존재 여부 검증
        Comment comment = commentRepository.findById(id)
                .orElseThrow(() -> new AppException(ErrorCode.COMMENT_NOT_FOUND, ErrorCode.COMMENT_NOT_FOUND.getMessage()));
        log.info("댓글 찾음:{}", comment.getComment());

        log.info("수정자:{}", authentication.getName());
        log.info("작성자:{}", comment.getUser().getUserName());

        // 2. 댓글 작성자 == 수정 요청자
        if (!authentication.getName().equals(comment.getUser().getUserName())) {
            throw new AppException(ErrorCode.INVALID_PERMISSION, ErrorCode.INVALID_PERMISSION.getMessage());
        }

        // 3. 댓글 수정
        CommentEditor.CommentEditorBuilder builder = comment.toEditor();
        CommentEditor commentEditor = builder.comment(request.getComment())
                .build();

        comment.edit(commentEditor);

        return CommentDTO.builder()
                .id(comment.getId())
                .comment(comment.getComment())
                .user(comment.getUser())
                .post(comment.getPost())
                .registeredAt(comment.getRegisteredAt())
                .updatedAt(comment.getUpdatedAt())
                .build();
    }


    /*--- 댓글 삭제 ---*/
    public void deleteComment(Long id, Authentication authentication) {

        // TODO: admin 권한

        // 1. 댓글 존재 여부 검증
        Comment comment = commentRepository.findById(id)
                .orElseThrow(() -> new AppException(ErrorCode.COMMENT_NOT_FOUND, ErrorCode.COMMENT_NOT_FOUND.getMessage()));

        // 2. 댓글 작성자 == 삭제 요청자
        if (!authentication.getName().equals(comment.getUser().getUserName())) {
            throw new AppException(ErrorCode.INVALID_PERMISSION, ErrorCode.INVALID_PERMISSION.getMessage());
        }
        log.info("요청자 닉넴:{}", authentication.getName());

        // 3. 댓글 삭제
        commentRepository.delete(comment);
    }

    /*--- 댓글 작성 ---*/
    public CommentDTO addComment(CommentRequest request, Long postId, Authentication authentication) {

        // 1. 로그인 검증
        User user = userRepository.findByUserName(authentication.getName())
                .orElseThrow(() -> new AppException(ErrorCode.USERNAME_NOT_FOUND, ErrorCode.USERNAME_NOT_FOUND.getMessage()));

        // 2. 포스트 존재 여부 검증
        Post post = postRepository.findById(postId)
                .orElseThrow(() -> new AppException(ErrorCode.POST_NOT_FOUND, ErrorCode.POST_NOT_FOUND.getMessage()));

        // 3. 댓글 저장
        Comment comment = commentRepository.save(request.toEntity(user, post));
        alarmRepository.save(Alarm.addAlarm(AlarmType.NEW_COMMENT_ON_POST,post.getUser(), user.getUserId(), post.getPostId()));


        return CommentDTO.builder()
                .id(comment.getId())
                .comment(comment.getComment())
                .user(comment.getUser())
                .post(comment.getPost())
                .registeredAt(comment.getRegisteredAt())
                .updatedAt(comment.getUpdatedAt())
                .build();
    }

}
