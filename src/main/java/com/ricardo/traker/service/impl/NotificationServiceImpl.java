package com.ricardo.traker.service.impl;

import com.ricardo.traker.model.entity.AlertEntity.AlertEntity;
import com.ricardo.traker.model.entity.NotificationEntity;
import com.ricardo.traker.model.entity.PositionEntity;
import com.ricardo.traker.repository.NotificationRepository;
import com.ricardo.traker.service.NotificationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.NoSuchElementException;

@Service
public class NotificationServiceImpl implements NotificationService {

    @Autowired
    NotificationRepository notificationRepository;

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
}
