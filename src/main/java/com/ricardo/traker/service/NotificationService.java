package com.ricardo.traker.service;

import com.ricardo.traker.model.dto.response.ListResponse;
import com.ricardo.traker.model.dto.response.NotificationResponseDto;
import com.ricardo.traker.model.entity.AlertEntity.AlertEntity;
import com.ricardo.traker.model.entity.NotificationEntity;
import com.ricardo.traker.model.entity.PositionEntity;

import java.util.List;

public interface NotificationService {

    NotificationEntity createNotification(AlertEntity alert, PositionEntity position);

    void readNotification(Integer id);

    NotificationResponseDto getNotification(Integer id);

    ListResponse<NotificationResponseDto> getNotifications(Integer userId, Integer page, Integer size, String sort);

    ListResponse<NotificationResponseDto> getVehicleNotifications(Integer vehicleId, Integer page, Integer size, String sort);

    ListResponse<NotificationResponseDto> getAlertNotifications(Integer alertId, Integer page, Integer size, String sort);

    ListResponse<NotificationResponseDto> getPendingNotifications(Integer userId, Integer page, Integer size, String sort);

}
