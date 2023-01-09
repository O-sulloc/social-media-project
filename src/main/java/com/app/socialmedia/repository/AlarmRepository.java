package com.app.socialmedia.repository;

import com.app.socialmedia.domain.entity.Alarm;
import com.app.socialmedia.domain.entity.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AlarmRepository extends JpaRepository<Alarm, Long> {
    Page<Alarm> findAllByUser(User user, Pageable pageable);

}
