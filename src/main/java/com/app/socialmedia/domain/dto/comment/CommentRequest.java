package com.app.socialmedia.domain.dto.comment;

import com.app.socialmedia.domain.entity.Comment;
import com.app.socialmedia.domain.entity.Post;
import com.app.socialmedia.domain.entity.User;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class CommentRequest {

    private String comment;

    public Comment toEntity(User user, Post post) {
        return Comment.builder()
                .comment(this.comment)
                .post(post)
                .user(user)
                .build();
    }
}
