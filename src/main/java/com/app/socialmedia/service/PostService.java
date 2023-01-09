package com.app.socialmedia.service;

import com.app.socialmedia.domain.dto.post.*;
import com.app.socialmedia.domain.entity.Post;
import com.app.socialmedia.domain.entity.User;
import com.app.socialmedia.exception.AppException;
import com.app.socialmedia.exception.ErrorCode;
import com.app.socialmedia.repository.PostRepository;
import com.app.socialmedia.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@RequiredArgsConstructor
@Slf4j
public class PostService {

    private final PostRepository postRepository;

    private final UserRepository userRepository;

    public PageInfoResponse getList(Pageable pageable) {

        Page<Post> postList = postRepository.findAll(pageable);

        //dto로 변환
        Page<PostGetOneResponse> postResponse = postList.map(
                post -> PostGetOneResponse.builder()
                        .id(post.getPostId())
                        .body(post.getBody())
                        .title(post.getTitle())
                        .userName(post.getUser().getUserName())
                        .createdAt(post.getRegisteredAt())
                        .lastModifiedAt(post.getUpdatedAt())
                        .build());

        PageInfoResponse pageInfoResponse = PageInfoResponse.builder()
                .content(postResponse.getContent())
                .pageable("INSTANCE")
                .last(postResponse.hasNext()) //다음 글이 없으면 False를 리턴한다.
                .totalPages(postResponse.getTotalPages())
                .size(postResponse.getSize())
                .number(postResponse.getNumber())
                .sort(postResponse.getSort())
                .first(postResponse.isFirst())
                .numberOfElements(postResponse.getNumberOfElements())
                .empty(postResponse.isEmpty())
                .build();

        return pageInfoResponse;
    }

    public void delete(Long postId, Authentication authentication) {
        Post post = postRepository.findById(postId)
                .orElseThrow(() -> new AppException(ErrorCode.POST_NOT_FOUND, ErrorCode.POST_NOT_FOUND.getMessage()));

        log.info("writer name:{}", post.getUser().getUserName());
        log.info("editor name:{}", authentication.getName());

        // 삭제하려는 사람 != 글쓴이
        if (!authentication.getName().equals(post.getUser().getUserName())) {
            throw new AppException(ErrorCode.INVALID_PERMISSION, ErrorCode.INVALID_PERMISSION.getMessage());
        }

        // TODO: 삭제하려는 사람 == ADMIN

        postRepository.delete(post);
    }

    @Transactional
    public void update(Long postId, PostUpdateRequest request, Authentication authentication) {
        Post post = postRepository.findById(postId)
                .orElseThrow(() -> new AppException(ErrorCode.POST_NOT_FOUND, ErrorCode.POST_NOT_FOUND.getMessage()));

        log.info("writer name:{}", post.getUser().getUserName());
        log.info("editor name:{}", authentication.getName());

        // 수정하려는 사람 != 글쓴이
        if (!authentication.getName().equals(post.getUser().getUserName())) {
            throw new AppException(ErrorCode.INVALID_PERMISSION, ErrorCode.INVALID_PERMISSION.getMessage());
        }


        // TODO: 수정하려는 사람 == ADMIN

        PostEditor.PostEditorBuilder builder = post.toEditor();

        PostEditor postEditor = builder.title(request.getTitle())
                .body(request.getBody())
                .build();

        post.edit(postEditor);
    }

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

        return PostDTO.builder()
                .postId(post.getPostId())
                .title(post.getTitle())
                .body(post.getBody())
                .build();
    }
}
