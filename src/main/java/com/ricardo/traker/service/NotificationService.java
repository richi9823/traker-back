package com.ricardo.traker.service;

import com.ricardo.traker.model.dto.response.NotificationResponseDto;
import com.ricardo.traker.model.entity.AlertEntity.AlertEntity;
import com.ricardo.traker.model.entity.NotificationEntity;
import com.ricardo.traker.model.entity.PositionEntity;

import java.util.List;

public interface NotificationService {

    NotificationEntity createNotification(AlertEntity alert, PositionEntity position);

    void readNotification(Integer id);

    List<NotificationResponseDto> getNotifications(Integer userId);

    List<NotificationResponseDto> getVehicleNotifications(Integer vehicleId);

    List<NotificationResponseDto> getAlertNotifications(Integer alertId);

    List<NotificationResponseDto> getPendingNotifications(Integer userId);

}
