package com.app.socialmedia.domain.dto;

import lombok.*;

import java.time.LocalDateTime;

@Getter
@AllArgsConstructor
@Builder
@NoArgsConstructor
public class UserDTO {
    private Long id;
    private String userName;
    private String password;
}
