package com.app.socialmedia.domain.dto.user;

import lombok.*;

@Getter
@AllArgsConstructor
@Builder
@NoArgsConstructor
public class UserDTO {
    private Long userId;
    private String userName;
    private String password;
}
