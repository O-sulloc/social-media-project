package com.app.socialmedia.domain.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class User extends Base{
    //base 클래스 상속 (가입일, 수정일)

    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    private Long userId; //Integer

    @Column(unique = true) //username 컬럼 unique 제약 조건
    private String userName;
    private String password;

    //UserRole
}
