package com.app.socialmedia.service;

import com.app.socialmedia.domain.entity.Like;
import com.app.socialmedia.domain.entity.Post;
import com.app.socialmedia.domain.entity.User;
import com.app.socialmedia.exception.AppException;
import com.app.socialmedia.exception.ErrorCode;
import com.app.socialmedia.repository.LikeRepository;
import com.app.socialmedia.repository.PostRepository;
import com.app.socialmedia.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Slf4j
public class LikeService {

    private final LikeRepository likeRepository;
    private final PostRepository postRepository;
    private final UserRepository userRepository;

    /*--- 좋아요 추가 ---*/
    //@Transactional
    public boolean addLike(Authentication authentication, Long postId) {

        // 1. 로그인 검증
        User user = userRepository.findByUserName(authentication.getName())
                .orElseThrow(() -> new AppException(ErrorCode.USERNAME_NOT_FOUND, ErrorCode.USERNAME_NOT_FOUND.getMessage()));

        // 2. 포스트 존재 여부 확인
        Post post = postRepository.findById(postId)
                .orElseThrow(() -> new AppException(ErrorCode.POST_NOT_FOUND, ErrorCode.POST_NOT_FOUND.getMessage()));
        log.info("좋아요 누를 포스트:{}", post.getPostId());

        // 3. 좋아요 중복 여부 확인 (true면 아직 좋아요 안 누른 상태)
        if (isNotAlreadyLike(user, post)) {
            likeRepository.save(new Like(user, post));

            return true;
        }

        log.info("이미 좋아요 누름");

        return false;
    }

    /*--- 좋아요 중복 검증 ---*/
    // true면 아직 좋아요 안 누른 상태
    private boolean isNotAlreadyLike(User user, Post post) {
        return likeRepository.findByUserAndPost(user, post).isEmpty();
    }

}
