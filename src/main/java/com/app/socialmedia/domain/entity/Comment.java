package com.app.socialmedia.domain.entity;

import com.app.socialmedia.domain.dto.comment.CommentEditor;
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
@Setter
@SQLDelete(sql = "UPDATE comment SET deleted_at = current_timestamp WHERE id = ?")
@Where(clause = "deleted_at is null")
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

    private LocalDateTime deletedAt;

    public CommentEditor.CommentEditorBuilder toEditor() {
        return CommentEditor.builder()
                .comment(comment);
    }

    public void edit(CommentEditor commentEditor) {
        comment = commentEditor.getComment();
    }
}
