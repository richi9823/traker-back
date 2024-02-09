package com.ricardo.traker.service;

import com.ricardo.traker.mapper.NotificationMapper;
import com.ricardo.traker.model.dto.response.ListResponse;
import com.ricardo.traker.model.dto.response.NotificationResponseDto;
import com.ricardo.traker.model.entity.AlertEntity.AlertEntity;
import com.ricardo.traker.model.entity.NotificationEntity;
import com.ricardo.traker.model.entity.PositionEntity;
import com.ricardo.traker.repository.AlertRepository;
import com.ricardo.traker.repository.NotificationRepository;
import com.ricardo.traker.service.NotificationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.util.NoSuchElementException;
import java.util.stream.Collectors;

@Service
public class NotificationService {

    @Autowired
    NotificationRepository notificationRepository;

    @Autowired
    NotificationMapper notificationMapper;


    public NotificationEntity createNotification(AlertEntity alert, PositionEntity position) {
        NotificationEntity notificationEntity = new NotificationEntity();
        notificationEntity.setAlert(alert);
        notificationEntity.setRead(false);
        return notificationRepository.save(notificationEntity);
    }


    public void readNotification(Long id) {
        NotificationEntity notificationEntity = notificationRepository.findById(id).orElseThrow(() -> new NoSuchElementException("notification not found"));
        notificationEntity.setRead(true);
        notificationRepository.save(notificationEntity);
    }


    public NotificationResponseDto getNotification(Long id) {
        return notificationMapper.mapNotificationEntityToNotificationResponseDto(
                notificationRepository.findById(id).orElseThrow(() -> new NoSuchElementException("notification not found"))
        );
    }


    public ListResponse<NotificationResponseDto> getNotifications(Long userId, Integer page, Integer size, String sort) {
        Page<NotificationEntity> result = notificationRepository.findAll(Specification.where(NotificationRepository.userIs(userId)), PageRequest.of(page, size, Sort.by(Sort.Direction.DESC, sort)));
        ListResponse<NotificationResponseDto> response = new ListResponse<>();
        response.setItems(result.get().map(notificationMapper::mapNotificationEntityToNotificationResponseDto)
                .collect(Collectors.toList()));
        response.setTotal(result.getTotalElements());

        return response;
    }


    public ListResponse<NotificationResponseDto> getVehicleNotifications(Long vehicleId, Integer page, Integer size, String sort) {
        Page<NotificationEntity> result = notificationRepository.findAll(Specification.where(NotificationRepository.vehicleIs(vehicleId)), PageRequest.of(page, size, Sort.by(Sort.Direction.DESC, sort)));
        ListResponse<NotificationResponseDto> response = new ListResponse<>();
        response.setItems(result.get().map(notificationMapper::mapNotificationEntityToNotificationResponseDto)
                .collect(Collectors.toList()));
        response.setTotal(result.getTotalElements());

        return response;
    }


    public ListResponse<NotificationResponseDto> getAlertNotifications(Long alertId, Integer page, Integer size, String sort) {
        Page<NotificationEntity> result = notificationRepository.findAll(Specification.where(NotificationRepository.alertIs(alertId)), PageRequest.of(page, size, Sort.by(Sort.Direction.DESC, sort)));
        ListResponse<NotificationResponseDto> response = new ListResponse<>();
        response.setItems(result.get().map(notificationMapper::mapNotificationEntityToNotificationResponseDto)
                .collect(Collectors.toList()));
        response.setTotal(result.getTotalElements());

        return response;
    }


    public ListResponse<NotificationResponseDto> getPendingNotifications(Long userId, Integer page, Integer size, String sort) {
        Page<NotificationEntity> result = notificationRepository.findAll(Specification.where(NotificationRepository.userIs(userId)).and(NotificationRepository.readIs(true)), PageRequest.of(page, size, Sort.by(Sort.Direction.DESC, sort)));
        ListResponse<NotificationResponseDto> response = new ListResponse<>();
        response.setItems(result.get().map(notificationMapper::mapNotificationEntityToNotificationResponseDto)
                .collect(Collectors.toList()));
        response.setTotal(result.getTotalElements());

        return response;
    }

    public void deleteByAlertId(long id){
        notificationRepository.deleteByAlert_Id(id);
    }

    public void deleteById(long id){
        notificationRepository.deleteById(id);
    }
}
