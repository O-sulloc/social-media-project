package com.app.socialmedia.repository;

import com.app.socialmedia.domain.entity.Like;
import com.app.socialmedia.domain.entity.Post;
import com.app.socialmedia.domain.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Transactional(readOnly = true)
public interface LikeRepository extends JpaRepository<Like, Long> {
    Optional<Like> findByUserAndPost(User user, Post post);

    Long countByPost(Post post);
}
