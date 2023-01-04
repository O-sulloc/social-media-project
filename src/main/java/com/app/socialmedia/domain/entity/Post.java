package com.app.socialmedia.domain.entity;

import com.app.socialmedia.domain.dto.post.PostDTO;
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

    public PostDTO toDTO() {
        return new PostDTO(this.postId, this.body, this.title, this.getUser().getUserName(), this.getRegisteredAt(), this.getUpdatedAt());
    }

    public PostEditor.PostEditorBuilder toEditor(){
        return PostEditor.builder()
                .title(title)
                .body(body);
    }

    public void edit(PostEditor postEditor){
        title = postEditor.getTitle();
        body = postEditor.getBody();
    }

}
