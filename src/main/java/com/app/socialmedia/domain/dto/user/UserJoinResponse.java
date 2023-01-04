package com.app.socialmedia.domain.dto.user;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class UserJoinResponse {

    private Long userId;
    private String userName;
}
