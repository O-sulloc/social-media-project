package com.app.socialmedia.repository;

import com.app.socialmedia.domain.entity.Comment;
import com.app.socialmedia.domain.entity.Post;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CommentRepository extends JpaRepository<Comment, Long> {

    //Page<Comment> findByPostId(Long postId, Pageable pageable);
    Page<Comment> findByPost(Post post, Pageable pageable);
}
