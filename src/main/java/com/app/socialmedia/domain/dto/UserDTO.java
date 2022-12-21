package com.app.socialmedia.domain.dto;

import lombok.*;

@Getter
@AllArgsConstructor
@Builder
@NoArgsConstructor
public class UserDTO {
    private Long id;
    private String userName;
    private String password;

}
