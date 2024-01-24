package com.ricardo.traker.service.impl;

import com.ricardo.traker.enums.TypeAlertEnum;
import com.ricardo.traker.mapper.AlertMapper;
import com.ricardo.traker.model.dto.request.AlertRequest.AlertRequestDto;
import com.ricardo.traker.model.dto.response.AlertResponse.AlertResponseDto;
import com.ricardo.traker.model.dto.response.ListResponse;
import com.ricardo.traker.model.entity.AlertEntity.AlertArrivalEntity;
import com.ricardo.traker.model.entity.AlertEntity.AlertDistanceEntity;
import com.ricardo.traker.model.entity.AlertEntity.AlertEntity;
import com.ricardo.traker.model.entity.AlertEntity.AlertSpeedEntity;
import com.ricardo.traker.model.entity.NotificationEntity;
import com.ricardo.traker.model.entity.PositionEntity;
import com.ricardo.traker.model.entity.VehicleEntity;
import com.ricardo.traker.repository.AlertArrivalRepository;
import com.ricardo.traker.repository.AlertDistanceRepository;
import com.ricardo.traker.repository.AlertRepository;
import com.ricardo.traker.repository.AlertSpeedRepository;
import com.ricardo.traker.service.AlertService;
import com.ricardo.traker.service.NotificationService;
import com.ricardo.traker.service.VehicleService;
import com.ricardo.traker.util.CompareDate;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@Slf4j
public class AlertServiceImpl implements AlertService {

    @Autowired
    VehicleService vehicleService;

    @Autowired
    AlertRepository alertRepository;

    @Autowired
    AlertDistanceRepository alertDistanceRepository;

    @Autowired
    AlertArrivalRepository alertArrivalRepository;

    @Autowired
    AlertSpeedRepository alertSpeedRepository;

    @Autowired
    AlertMapper alertMapper;

    @Autowired
    NotificationService notificationService;


    @Override
    public AlertResponseDto createAlert(AlertRequestDto alertRequestDto) {
        VehicleEntity vehicleEntity = vehicleService.getVehicleEntity(alertRequestDto.getVehicleId()).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "vehicle not found"));
        AlertEntity alertEntity = alertMapper.mapAlertRequestDtoToAlertEntity(alertRequestDto);
        alertEntity.setVehicle(vehicleEntity);
        this.save(alertEntity);
        return alertMapper.mapAlertEntityToAlertResponseDto(alertEntity);
    }

    @Override
    public AlertResponseDto getAlert(Integer id) {
        return alertMapper.mapAlertEntityToAlertResponseDto(
                alertRepository.findById(id).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Alert not found"))
        );
    }

    @Override
    public AlertResponseDto editAlert(Integer alertId, AlertRequestDto alertRequestDto) {

        AlertEntity alertEntity = alertRepository.findById(alertId).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Alert not found"));
        return switch (alertEntity.getType()){
            case ARRIVAL -> {
                AlertArrivalEntity alertArrivalEntity = alertArrivalRepository.findById(alertId).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Alert not found"));
                alertArrivalEntity = alertMapper.mapAlertRequestDtoToAlertArrivalEntity(alertRequestDto, alertArrivalEntity);
                this.save(alertArrivalEntity);
                yield alertMapper.mapAlertEntityToAlertResponseDto(alertArrivalEntity);
            }
            case SPEED -> {
                AlertSpeedEntity alertSpeedEntity = alertSpeedRepository.findById(alertId).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Alert not found"));
                alertSpeedEntity = alertMapper.mapAlertRequestDtoToAlertSpeedEntity(alertRequestDto, alertSpeedEntity);
                this.save(alertSpeedEntity);
                yield alertMapper.mapAlertEntityToAlertResponseDto(alertSpeedEntity);
            }
            case DISTANCE -> {
                AlertDistanceEntity alertDistanceEntity = alertDistanceRepository.findById(alertId).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Alert not found"));
                alertDistanceEntity = alertMapper.mapAlertRequestDtoToAlertDistanceEntity(alertRequestDto, alertDistanceEntity);
                this.save(alertDistanceEntity);
                yield alertMapper.mapAlertEntityToAlertResponseDto(alertDistanceEntity);
            }
        };

    }

    @Override
    public void removeAlert(Integer alertId) {
        alertRepository.deleteById(alertId);
    }

    @Override
    public List<AlertResponseDto> getVehicleAlerts(Integer vehicleId) {
        return alertRepository.findByVehicle_Id(vehicleId).stream().map(alertMapper::mapAlertEntityToAlertResponseDto).collect(Collectors.toList());
    }

    @Override
    public ListResponse<AlertResponseDto> getVehicleAlerts(Integer vehicleId, Integer page, Integer size, String sort) {
        Page<AlertEntity> result = alertRepository.findAll(Specification.where(AlertRepository.vehicleIs(vehicleId)), PageRequest.of(page, size, Sort.by(Sort.Direction.DESC, sort)));
        ListResponse<AlertResponseDto> response = new ListResponse<>();
        response.setItems(result.get().map(alertMapper::mapAlertEntityToAlertResponseDto).collect(Collectors.toList()));
        response.setTotal(result.getTotalElements());
        return response;
    }

    @Override
    public Optional<AlertEntity> getAlertEntity(Integer alertId) {
        return alertRepository.findById(alertId);
    }

    @Override
    public void checkAlerts(PositionEntity newPosition) {
        vehicleService.getVehicleEntityByGpsId(newPosition.getGps().getTraccarDeviceId()).ifPresent(
                v -> {
                    List<AlertResponseDto> alerts = this.getVehicleAlerts(v.getId());
                    alerts.stream().forEach(a -> {
                        switch (a.getType()){
                            case DISTANCE -> checkDistance(a.getId(), newPosition);
                            case ARRIVAL -> checkArrival(a.getId(), newPosition);
                            case SPEED -> checkSpeed(a.getId(), newPosition);
                        }
                    });
                }
        );
    }

    private void checkDistance(Integer id, PositionEntity position){
        alertDistanceRepository.findById(id).ifPresent(
                a ->{
                   BigDecimal distanceKm = BigDecimal.valueOf(Math.acos(Math.sin(a.getPointReferenceLatitude().doubleValue()) * Math.sin(position.getLatitude().doubleValue())
                            + Math.cos(a.getPointReferenceLatitude().doubleValue())* Math.cos(position.getLatitude().doubleValue()) * Math.cos(position.getLongitude().doubleValue() - a.getPointReferenceLongitude().doubleValue()))
                           * 6371);
                   if(a.getMaxDistance().compareTo(distanceKm) < 0){
                       Optional<NotificationEntity> notifications = Optional.empty();
                       if(a.getNotifications() != null){
                            notifications = a.getNotifications().stream().filter( n-> !n.isRead()).reduce(CompareDate:: maxNotificationEntity);
                       }

                       if(!notifications.isEmpty() && !notifications.get().getCreatedDate().isBefore(LocalDateTime.now().minusMinutes(10))){
                           position.setNotification(notifications.get());
                       }else{
                           NotificationEntity notificationEntity = notificationService.createNotification(a, position);
                           position.setNotification(notificationEntity);
                       }

                   }

                }
        );
    }

    private void checkArrival(Integer id, PositionEntity position){
        alertArrivalRepository.findById(id).ifPresent(a ->{
            BigDecimal distanceKm = BigDecimal.valueOf(Math.acos(Math.sin(a.getLatitude().doubleValue()) * Math.sin(position.getLatitude().doubleValue())
                    + Math.cos(a.getLatitude().doubleValue())* Math.cos(position.getLatitude().doubleValue()) * Math.cos(position.getLongitude().doubleValue() - a.getLongitude().doubleValue()))
                    * 6371);
            if(BigDecimal.valueOf(10).compareTo(distanceKm) > 0){
                Optional<NotificationEntity> notifications = a.getNotifications().stream().filter( n-> !n.isRead()).reduce(CompareDate:: maxNotificationEntity);
                if(!notifications.isEmpty() && !notifications.get().getCreatedDate().isBefore(LocalDateTime.now().minusMinutes(30))){
                    position.setNotification(notifications.get());
                }else{
                    NotificationEntity notificationEntity = notificationService.createNotification(a, position);
                    position.setNotification(notificationEntity);
                }

            }
        });


    }

    private void checkSpeed(Integer id, PositionEntity position){
        alertSpeedRepository.findById(id).ifPresent(a ->{
            if(a.getSpeedLimit().compareTo(position.getSpeed()) < 0){
                Optional<NotificationEntity> notifications = a.getNotifications().stream().filter( n-> !n.isRead()).reduce(CompareDate:: maxNotificationEntity);
                if(!notifications.isEmpty() && !notifications.get().getCreatedDate().isBefore(LocalDateTime.now().minusMinutes(5))){
                    position.setNotification(notifications.get());
                }else{
                    NotificationEntity notificationEntity = notificationService.createNotification(a, position);
                    position.setNotification(notificationEntity);
                }

            }
        });
    }


    private void save(AlertEntity alertEntity){
        if(alertEntity instanceof AlertSpeedEntity){
            alertSpeedRepository.save((AlertSpeedEntity) alertEntity);
        }else if(alertEntity instanceof AlertArrivalEntity){
            alertArrivalRepository.save((AlertArrivalEntity) alertEntity);
        }else if (alertEntity instanceof AlertDistanceEntity){
            alertDistanceRepository.save((AlertDistanceEntity) alertEntity);
        }
    }
}
