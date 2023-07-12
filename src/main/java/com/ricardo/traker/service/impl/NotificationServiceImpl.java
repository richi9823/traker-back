package com.ricardo.traker.service.impl;

import com.ricardo.traker.mapper.NotificationMapper;
import com.ricardo.traker.model.dto.response.NotificationResponseDto;
import com.ricardo.traker.model.entity.AlertEntity.AlertEntity;
import com.ricardo.traker.model.entity.NotificationEntity;
import com.ricardo.traker.model.entity.PositionEntity;
import com.ricardo.traker.repository.NotificationRepository;
import com.ricardo.traker.service.NotificationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.stream.Collectors;

@Service
public class NotificationServiceImpl implements NotificationService {

    @Autowired
    NotificationRepository notificationRepository;

    @Autowired
    NotificationMapper notificationMapper;

    @Override
    public NotificationEntity createNotification(AlertEntity alert, PositionEntity position) {
        NotificationEntity notificationEntity = new NotificationEntity();
        notificationEntity.setAlert(alert);
        notificationEntity.setRead(false);
        return notificationRepository.save(notificationEntity);
    }

    @Override
    public void readNotification(Integer id) {
        NotificationEntity notificationEntity = notificationRepository.findById(id).orElseThrow(() -> new NoSuchElementException("notification not found"));
        notificationEntity.setRead(true);
        notificationRepository.save(notificationEntity);
    }

    @Override
    public List<NotificationResponseDto> getNotifications(Integer userId) {
        return notificationRepository.findByAlert_Vehicle_User_Id(userId)
                .stream().map(notificationMapper::mapNotificationEntityToNotificationResponseDto)
                .collect(Collectors.toList());
    }

    @Override
    public List<NotificationResponseDto> getVehicleNotifications(Integer vehicleId) {
        return notificationRepository.findByAlert_Vehicle_Id(vehicleId)
                .stream().map(notificationMapper::mapNotificationEntityToNotificationResponseDto)
                .collect(Collectors.toList());
    }

    @Override
    public List<NotificationResponseDto> getAlertNotifications(Integer alertId) {
        return notificationRepository.findByAlert_Id(alertId)
                .stream().map(notificationMapper::mapNotificationEntityToNotificationResponseDto)
                .collect(Collectors.toList());
    }

    @Override
    public List<NotificationResponseDto> getPendingNotifications(Integer userId) {
        return notificationRepository.findByAlert_Vehicle_User_IdAndReadFalse(userId)
                .stream().map(notificationMapper::mapNotificationEntityToNotificationResponseDto)
                .collect(Collectors.toList());
    }
}
