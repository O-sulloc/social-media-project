package com.app.socialmedia.service;

import com.app.socialmedia.domain.dto.alarm.AlarmType;
import com.app.socialmedia.domain.entity.Alarm;
import com.app.socialmedia.domain.entity.Like;
import com.app.socialmedia.domain.entity.Post;
import com.app.socialmedia.domain.entity.User;
import com.app.socialmedia.exception.AppException;
import com.app.socialmedia.exception.ErrorCode;
import com.app.socialmedia.repository.AlarmRepository;
import com.app.socialmedia.repository.LikeRepository;
import com.app.socialmedia.repository.PostRepository;
import com.app.socialmedia.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
@Slf4j
public class LikeService {

    private final LikeRepository likeRepository;
    private final PostRepository postRepository;
    private final UserRepository userRepository;
    private final AlarmRepository alarmRepository;


    /*--- 좋아요 개수 리턴 ---*/
    public Long getLike(Long postId) {

        // 1. 포스트 존재 여부 확인
        Post post = postRepository.findById(postId)
                .orElseThrow(() -> new AppException(ErrorCode.POST_NOT_FOUND, ErrorCode.POST_NOT_FOUND.getMessage()));

        // 2. 포스트 좋아요 개수 리턴
        return likeRepository.countByPost(post);

    }


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

        // 3. 좋아요 중복 여부 확인
        Optional<Like> like = likeRepository.findByUserAndPost(user, post);

        if (like.isPresent()) {
            likeRepository.delete(like.get()); // 삭제

            return false;
        } else {
            likeRepository.save(new Like(user, post));
            alarmRepository.save(Alarm.addAlarm(AlarmType.NEW_LIKE_ON_POST, post.getUser(), user.getUserId(), post.getPostId()));

            return true;
        }
    }

}
