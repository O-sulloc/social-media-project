package com.app.socialmedia.domain.dto.post;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.List;

@Getter
@AllArgsConstructor
public class PostList {

    private List<?> content;
    private int totalPage;
}
