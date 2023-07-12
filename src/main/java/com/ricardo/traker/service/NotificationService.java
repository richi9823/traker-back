package com.ricardo.traker.service;

import com.ricardo.traker.model.entity.AlertEntity.AlertEntity;
import com.ricardo.traker.model.entity.NotificationEntity;
import com.ricardo.traker.model.entity.PositionEntity;

public interface NotificationService {

    NotificationEntity createNotification(AlertEntity alert, PositionEntity position);

    void readNotification(Integer id);
}
