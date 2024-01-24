package com.ricardo.traker.service.impl;

import com.ricardo.traker.mapper.NotificationMapper;
import com.ricardo.traker.model.dto.response.ListResponse;
import com.ricardo.traker.model.dto.response.NotificationResponseDto;
import com.ricardo.traker.model.dto.response.VehicleResponseDto;
import com.ricardo.traker.model.entity.AlertEntity.AlertEntity;
import com.ricardo.traker.model.entity.NotificationEntity;
import com.ricardo.traker.model.entity.PositionEntity;
import com.ricardo.traker.model.entity.VehicleEntity;
import com.ricardo.traker.repository.NotificationRepository;
import com.ricardo.traker.repository.VehicleRepository;
import com.ricardo.traker.service.NotificationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
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
    public NotificationResponseDto getNotification(Integer id) {
        return notificationMapper.mapNotificationEntityToNotificationResponseDto(
                notificationRepository.findById(id).orElseThrow(() -> new NoSuchElementException("notification not found"))
        );
    }

    @Override
    public ListResponse<NotificationResponseDto> getNotifications(Integer userId, Integer page, Integer size, String sort) {
        Page<NotificationEntity> result = notificationRepository.findAll(Specification.where(NotificationRepository.userIs(userId)), PageRequest.of(page, size, Sort.by(Sort.Direction.DESC, sort)));
        ListResponse<NotificationResponseDto> response = new ListResponse<>();
        response.setItems(result.get().map(notificationMapper::mapNotificationEntityToNotificationResponseDto)
                .collect(Collectors.toList()));
        response.setTotal(result.getTotalElements());

        return response;
    }

    @Override
    public ListResponse<NotificationResponseDto> getVehicleNotifications(Integer vehicleId, Integer page, Integer size, String sort) {
        Page<NotificationEntity> result = notificationRepository.findAll(Specification.where(NotificationRepository.vehicleIs(vehicleId)), PageRequest.of(page, size, Sort.by(Sort.Direction.DESC, sort)));
        ListResponse<NotificationResponseDto> response = new ListResponse<>();
        response.setItems(result.get().map(notificationMapper::mapNotificationEntityToNotificationResponseDto)
                .collect(Collectors.toList()));
        response.setTotal(result.getTotalElements());

        return response;
    }

    @Override
    public ListResponse<NotificationResponseDto> getAlertNotifications(Integer alertId, Integer page, Integer size, String sort) {
        Page<NotificationEntity> result = notificationRepository.findAll(Specification.where(NotificationRepository.alertIs(alertId)), PageRequest.of(page, size, Sort.by(Sort.Direction.DESC, sort)));
        ListResponse<NotificationResponseDto> response = new ListResponse<>();
        response.setItems(result.get().map(notificationMapper::mapNotificationEntityToNotificationResponseDto)
                .collect(Collectors.toList()));
        response.setTotal(result.getTotalElements());

        return response;
    }

    @Override
    public ListResponse<NotificationResponseDto> getPendingNotifications(Integer userId, Integer page, Integer size, String sort) {
        Page<NotificationEntity> result = notificationRepository.findAll(Specification.where(NotificationRepository.userIs(userId)).and(NotificationRepository.readIs(true)), PageRequest.of(page, size, Sort.by(Sort.Direction.DESC, sort)));
        ListResponse<NotificationResponseDto> response = new ListResponse<>();
        response.setItems(result.get().map(notificationMapper::mapNotificationEntityToNotificationResponseDto)
                .collect(Collectors.toList()));
        response.setTotal(result.getTotalElements());

        return response;
    }
}
