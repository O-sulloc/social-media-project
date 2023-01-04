package com.app.socialmedia.domain.dto.comment;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class CommentDeleteResponse {

    private String message;
    private Long id;
}
