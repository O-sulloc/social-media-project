package com.app.socialmedia.service;

import com.app.socialmedia.domain.dto.comment.CommentAddRequest;
import com.app.socialmedia.domain.dto.comment.CommentDTO;
import com.app.socialmedia.domain.entity.Comment;
import com.app.socialmedia.domain.entity.Post;
import com.app.socialmedia.domain.entity.User;
import com.app.socialmedia.exception.AppException;
import com.app.socialmedia.exception.ErrorCode;
import com.app.socialmedia.repository.CommentRepository;
import com.app.socialmedia.repository.PostRepository;
import com.app.socialmedia.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class CommentService {

    private final UserRepository userRepository;
    private final CommentRepository commentRepository;
    private final PostRepository postRepository;

    /*--- 댓글 삭제  ---*/
    public void deleteComment(Long postId, Long id, Authentication authentication) {

        // 1. 댓글 존재 여부 검증
        Comment comment = commentRepository.findById(id)
                .orElseThrow(() -> new AppException(ErrorCode.COMMENT_NOT_FOUND, ErrorCode.COMMENT_NOT_FOUND.getMessage()));

        // 2. 댓글 작성자 == 삭제 요청자
        if (!authentication.getName().equals(comment.getUser().getUserName())) {
            throw new AppException(ErrorCode.INVALID_PERMISSION, ErrorCode.INVALID_PERMISSION.getMessage());
        }

        // 3. 댓글 삭제
        commentRepository.delete(comment);

    }

    /*--- 댓글 작성 ---*/
    public CommentDTO addComment(CommentAddRequest request, Long postId, Authentication authentication) {

        // 1. 로그인 검증
        User user = userRepository.findByUserName(authentication.getName())
                .orElseThrow(() -> new AppException(ErrorCode.USERNAME_NOT_FOUND, ErrorCode.USERNAME_NOT_FOUND.getMessage()));

        // 2. 포스트 존재 여부 검증
        Post post = postRepository.findById(postId)
                .orElseThrow(() -> new AppException(ErrorCode.POST_NOT_FOUND, ErrorCode.POST_NOT_FOUND.getMessage()));

        // 3. 댓글 저장
        Comment comment = commentRepository.save(request.toEntity(user, post));

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
