package com.app.socialmedia.domain.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class PostAddResponse {

    private String message;
    private Long postId;
}
