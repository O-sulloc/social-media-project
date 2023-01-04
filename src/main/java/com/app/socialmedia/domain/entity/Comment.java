package com.app.socialmedia.domain.entity;

import com.app.socialmedia.domain.dto.comment.CommentEditor;
import lombok.*;

import javax.persistence.*;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Builder
@Setter
public class Comment extends Base {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String comment;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    @ManyToOne
    @JoinColumn(name = "post_id")
    private Post post;

    public CommentEditor.CommentEditorBuilder toEditor() {
        return CommentEditor.builder()
                .comment(comment);
    }

    public void edit(CommentEditor commentEditor) {
        comment = commentEditor.getComment();
    }
}
