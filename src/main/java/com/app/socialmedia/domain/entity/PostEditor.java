package com.app.socialmedia.domain.entity;

import lombok.Builder;
import lombok.Getter;

@Getter
public class PostEditor {

    private String title;
    private String body;

    @Builder
    public PostEditor(String title, String body) {
        this.title = title;
        this.body = body;
    }
}
