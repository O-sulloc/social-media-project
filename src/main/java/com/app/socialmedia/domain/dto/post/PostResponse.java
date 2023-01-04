package com.app.socialmedia.domain.dto.post;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class PostResponse {

    private String message;
    private Long postId;
}
