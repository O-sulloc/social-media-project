package com.app.socialmedia.domain.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.Date;

@Entity
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class User {

    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    private Long id; //Integer

    @Column(unique = true) //username 컬럼 unique 제약 조건
    private String userName;
    private String password;

    //UserRole
    //private Date registeredAt; //가입일
    //private Date updatedAt; //수정일
    //private Date deletedAt; //탈퇴일


}
