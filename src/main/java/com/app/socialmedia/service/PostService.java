package com.app.socialmedia.service;

import com.app.socialmedia.domain.dto.PostAddRequest;
import com.app.socialmedia.domain.dto.PostDTO;
import com.app.socialmedia.domain.entity.Post;
import com.app.socialmedia.domain.entity.User;
import com.app.socialmedia.exception.AppException;
import com.app.socialmedia.exception.ErrorCode;
import com.app.socialmedia.repository.PostRepository;
import com.app.socialmedia.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class PostService {

    private final PostRepository postRepository;

    private final UserRepository userRepository;

    public PostDTO getOne(Long postId) {
        // 포스트 아이디로 글 하나 가져오기
        Optional<Post> post = Optional.ofNullable(postRepository.findById(postId)
                .orElseThrow(() -> new AppException(ErrorCode.POST_NOT_FOUND, ErrorCode.POST_NOT_FOUND.getMessage())));


        return post.get().toDTO();
    }

    public PostDTO addPost(PostAddRequest request, Authentication authentication) {
        User user = userRepository.findByUserName(authentication.getName())
                .orElseThrow(() -> new AppException(ErrorCode.USERNAME_NOT_FOUND, ErrorCode.USERNAME_NOT_FOUND.getMessage()));

        Post post = postRepository.save(request.toEntity(user));

        post.setUser(user); //ㅋㅋ 이게 최선인가.. 뭐 어쨌든 userId 들어가긴 함.. 되는게 중요한거겠지...

        return PostDTO.builder()
                .postId(post.getPostId())
                .title(post.getTitle())
                .body(post.getBody())
                .build();
    }
}
