package com.app.socialmedia.domain.dto;

import com.app.socialmedia.domain.entity.Post;
import com.app.socialmedia.domain.entity.User;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class PostAddRequest {

    private String title;
    private String body;

    public Post toEntity(User user) {
        return Post.builder()
                .title(this.title)
                .body(this.body)
                .user(user)
                .build();
    }
}
