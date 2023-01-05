package com.app.socialmedia.domain.dto.comment;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

import java.util.List;

@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CommentInfoResponse {

    private List<CommentResponse> content;
    private Pageable pageable;
    private Boolean last;
    private Integer totalPages;
    private Long totalElements;
    private Integer size;
    private Integer number;
    private Sort sort;
    private Integer numberOfElements;
    private Boolean first;
    private Boolean empty;

}
