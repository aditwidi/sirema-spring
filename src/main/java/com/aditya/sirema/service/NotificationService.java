package com.aditya.sirema.service;

import com.aditya.sirema.dto.NotificationDto;
import com.aditya.sirema.dto.RequestDto;

import java.util.List;

public interface NotificationService {
    public void createNotification(NotificationDto notificationDto);
    public List<NotificationDto> findTop4NotificationsByUserId(Long userId);
}
