package com.app.socialmedia.domain.dto.post;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PostDTO {
    private Long postId;
    private String body;
    private String title;
    private String userName; //작성자
    private LocalDateTime registeredAt; //작성일
    private LocalDateTime updatedAt; //수정일

    public PostGetOneResponse getOneResponse() {
        return new PostGetOneResponse(this.postId, this.body, this.title, this.userName, this.registeredAt, this.updatedAt);
    }
}
