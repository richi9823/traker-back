package com.ricardo.traker.service;

import com.ricardo.traker.mapper.NotificationMapper;
import com.ricardo.traker.model.dto.response.ListResponse;
import com.ricardo.traker.model.dto.response.NotificationResponseDto;
import com.ricardo.traker.model.dto.response.NotificationShortResponseDto;
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

import java.util.ArrayList;
import java.util.NoSuchElementException;
import java.util.Optional;
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
        notificationEntity.setPositions(new ArrayList<>());
        notificationEntity.getPositions().add(position);
        return notificationRepository.save(notificationEntity);
    }

    NotificationEntity addPositionToNotification(PositionEntity position, NotificationEntity notification){
        if(notification.getPositions().get(0).getRoute().getGps().getVehicle().getId().equals(position.getRoute().getGps().getVehicle().getId())){
            notification.getPositions().add(position);
            return notificationRepository.save(notification);
        }
        throw new RuntimeException("Exception saving notification");
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

    public Optional<NotificationEntity> getNotificationEntity(Long id) {
        return notificationRepository.findById(id);
    }


    public ListResponse<NotificationShortResponseDto> getNotifications(Long userId, Long vehicleId, Long alertId, Boolean readed, Integer page, Integer size, String sort) {
        Specification specification = Specification.where(NotificationRepository.userIs(userId)).and(NotificationRepository.readIs(readed));
        if(vehicleId != null){
            specification = specification.and(NotificationRepository.hasVehicle(vehicleId));
        }
        if(alertId != null){
            specification = specification.and(NotificationRepository.alertIs(alertId));
        }
        Page<NotificationEntity> result = notificationRepository.findAll(specification, PageRequest.of(page, size, Sort.by(Sort.Direction.DESC, sort)));
        ListResponse<NotificationShortResponseDto> response = new ListResponse<>();
        response.setItems(result.get().map(notificationMapper::mapNotificationEntityToNotificationShortResponseDto)
                .collect(Collectors.toList()));
        response.setTotal(result.getTotalElements());
        return response;
    }

    public void deleteByAlertId(long id){
        var notifications = notificationRepository.findByAlert_Id(id);
        for(var n: notifications){
            this.deleteById(n.getId());
        }
    }

    public void deleteById(Long id){
        notificationRepository.deleteById(id);
    }



}
