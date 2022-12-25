package com.app.socialmedia.domain.dto;

import com.app.socialmedia.domain.entity.Post;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class PostUpdateRequest {

    private String title;
    private String body;

    public Post toEntity() {
        return Post.builder()
                .title(this.title)
                .body(this.body)
                .build();
    }
}
