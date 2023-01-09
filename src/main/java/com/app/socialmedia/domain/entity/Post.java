package com.app.socialmedia.domain.entity;

import com.app.socialmedia.domain.dto.post.PostDTO;
import com.app.socialmedia.domain.dto.post.PostEditor;
import lombok.*;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.Where;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Builder
@SQLDelete(sql = "UPDATE post SET deleted_at = current_timestamp WHERE post_id = ?") // delete 메서드 호출 시 해당 쿼리가 실행된다.
@Where(clause = "deleted_at is null") // 삭제된 데이터는 filter로 걸러서 안 보여줌
public class Post extends Base {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long postId; //글 번호

    private String body; //글 내용
    private String title; //글 제목

    @ManyToOne
    @JoinColumn(name = "user_id") //id 외래키
    private User user;

    private LocalDateTime deletedAt;

    public PostDTO toDTO() {
        return new PostDTO(this.postId, this.body, this.title, this.getUser().getUserName(), this.getRegisteredAt(), this.getUpdatedAt());
    }

    public PostEditor.PostEditorBuilder toEditor() {
        return PostEditor.builder()
                .title(title)
                .body(body);
    }

    public void edit(PostEditor postEditor) {
        title = postEditor.getTitle();
        body = postEditor.getBody();
    }

}
