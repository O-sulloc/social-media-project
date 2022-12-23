package com.app.socialmedia.domain.entity;

import lombok.*;

import javax.persistence.*;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Builder
@Setter
public class Post extends Base {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long postId; //글 번호

    private String body; //글 내용
    private String title; //글 제목

    @ManyToOne
    @JoinColumn(name = "user_id") //id 외래키
    private User user;
}
