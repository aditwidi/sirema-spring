package com.aditya.sirema.repository;

import com.aditya.sirema.entity.Notification;
import com.aditya.sirema.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface NotificationRepository extends JpaRepository<Notification, Long> {
    List<Notification> findTop4ByUserIdOrderByTimestampDesc(Long userId);
}
