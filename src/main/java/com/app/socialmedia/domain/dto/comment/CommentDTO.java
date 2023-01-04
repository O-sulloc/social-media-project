package com.app.socialmedia.domain.dto.comment;

import com.app.socialmedia.domain.entity.Post;
import com.app.socialmedia.domain.entity.User;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Builder
public class CommentDTO {
    private Long id;
    private String comment;
    private User user;
    private Post post;
    private LocalDateTime registeredAt; //작성일
    private LocalDateTime updatedAt; //수정일

}
