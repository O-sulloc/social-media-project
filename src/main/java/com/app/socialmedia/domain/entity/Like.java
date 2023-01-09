package com.app.socialmedia.domain.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.Where;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Builder
@Table(name = "likes")
@SQLDelete(sql = "UPDATE social.likes SET deleted_at = current_timestamp WHERE id = ?") // delete 메서드 호출 시 해당 쿼리가 실행된다.
@Where(clause = "deleted_at is null")
public class Like extends Base {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "post_id")
    private Post post;

    private LocalDateTime deletedAt;

    public Like(User user, Post post) {
        this.user = user;
        this.post = post;
    }
}
