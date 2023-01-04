package com.app.socialmedia.domain.dto.comment;

import lombok.Builder;
import lombok.Getter;

@Getter
public class CommentEditor {

    private String comment;

    @Builder
    public CommentEditor(String comment) {
        this.comment = comment;
    }
}
