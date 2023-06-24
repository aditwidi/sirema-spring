package com.aditya.sirema.mapper;

import com.aditya.sirema.dto.NotificationDto;
import com.aditya.sirema.entity.Notification;

public class NotificationMapper {
    public static NotificationDto mapToNotificationDto(Notification notification) {
        NotificationDto notificationDto = NotificationDto.builder()
                .id(notification.getId())
                .user(UserMapper.mapToUserDto(notification.getUser()))
                .username(notification.getUsername())
                .message(notification.getMessage())
                .timestamp(notification.getTimestamp())
                .build();
        return notificationDto;
    }

    public static Notification mapToNotification(NotificationDto notificationDto) {
        Notification notification = Notification.builder()
                .id(notificationDto.getId())
                .user(UserMapper.mapToUser(notificationDto.getUser()))
                .username(notificationDto.getUsername())
                .message(notificationDto.getMessage())
                .timestamp(notificationDto.getTimestamp())
                .build();
        return notification;
    }
}
