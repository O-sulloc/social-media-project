package com.app.socialmedia.repository;

import com.app.socialmedia.domain.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {
}
