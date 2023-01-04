package com.app.socialmedia.domain.dto.user;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class UserLoginRequest {

    private String userName;
    private String password;

}
