package com.aditya.sirema.service;

import com.aditya.sirema.dto.NotificationDto;
import com.aditya.sirema.dto.UserDto;
import com.aditya.sirema.entity.Notification;
import com.aditya.sirema.entity.User;
import com.aditya.sirema.mapper.NotificationMapper;
import com.aditya.sirema.repository.NotificationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class NotificationServiceImpl implements NotificationService {
    @Autowired
    private NotificationRepository notificationRepository;

    @Autowired
    private UserService userService;

    @Override
    public void createNotification(NotificationDto notificationDto) {
        Notification notification = NotificationMapper.mapToNotification(notificationDto);
        notification.setUsername(notificationDto.getUser().getName()); // Set username from UserDto
        notification.setTimestamp(LocalDateTime.now()); // Set the current timestamp
        notificationRepository.save(notification);
    }

    @Override
    public List<NotificationDto> findTop4NotificationsByUserId(Long userId) {
        User user = userService.findUserById(userId);
        List<Notification> notifications = notificationRepository.findTop4ByUserIdOrderByTimestampDesc(userId);
        return notifications.stream()
                .map(NotificationMapper::mapToNotificationDto)
                .collect(Collectors.toList());
    }
}
