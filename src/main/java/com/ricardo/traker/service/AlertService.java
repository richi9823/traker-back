package com.ricardo.traker.service;

import com.ricardo.traker.mapper.AlertMapper;
import com.ricardo.traker.model.dto.request.AlertRequest.AlertRequestDto;
import com.ricardo.traker.model.dto.response.AlertResponse.AlertResponseDto;
import com.ricardo.traker.model.dto.response.AlertResponse.AlertShortResponseDto;
import com.ricardo.traker.model.dto.response.ListResponse;
import com.ricardo.traker.model.entity.AlertEntity.*;
import com.ricardo.traker.model.entity.NotificationEntity;
import com.ricardo.traker.model.entity.PositionEntity;
import com.ricardo.traker.model.entity.VehicleEntity;
import com.ricardo.traker.repository.*;
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
import java.time.OffsetDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@Slf4j
public class AlertService {
    @Autowired
    AlertDistanceRouteRepository alertDistanceRouteRepository;

    @Autowired
    AlertRepository alertRepository;

    @Autowired
    VehicleRepository vehicleRepository;

    @Autowired
    AlertDistanceRepository alertDistanceRepository;

    @Autowired
    AlertArrivalRepository alertArrivalRepository;

    @Autowired
    AlertSpeedRepository alertSpeedRepository;

    @Autowired
    UserRepository userRepository;

    @Autowired
    AlertMapper alertMapper;

    @Autowired
    NotificationService notificationService;;



    public AlertResponseDto createAlert(Long userId, AlertRequestDto alertRequestDto) {
        List<VehicleEntity> vehicles = new ArrayList<>();
        if(alertRequestDto.getVehicles() != null){
            alertRequestDto.getVehicles().stream().forEach(id->
                   vehicles.add(vehicleRepository.findById(id).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Vehicle not found id - " + id)))
            );
        }
        AlertEntity alertEntity = alertMapper.mapAlertRequestDtoToAlertEntity(alertRequestDto);
        alertEntity.setUser(userRepository.findById(userId).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "User not found")));
        alertEntity.setVehicles(vehicles);
        this.save(alertEntity);
        return alertMapper.mapAlertEntityToAlertResponseDto(alertEntity);
    }


    public AlertResponseDto getAlert(Long id) {
        return alertMapper.mapAlertEntityToAlertResponseDto(
                alertRepository.findById(id).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Alert not found"))
        );
    }


    public AlertResponseDto editAlert(Long alertId, AlertRequestDto alertRequestDto) {

        AlertEntity alertEntity = alertRepository.findById(alertId).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Alert not found"));
        List<VehicleEntity> vehicles = new ArrayList<>();
        if(alertRequestDto.getVehicles() != null){
            alertRequestDto.getVehicles().stream().forEach(id->
                    vehicles.add(vehicleRepository.findById(id).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Vehicle not found id - " + id)))
            );
            alertEntity.setVehicles(vehicles);
        }

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
            case DISTANCE_ROUTE -> {
                AlertDistanceRouteEntity alertDistanceRouteEntity = alertDistanceRouteRepository.findById(alertId).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Alert not found"));
                alertDistanceRouteEntity = alertMapper.mapAlertRequestDtoToAlertDistanceRouteEntity(alertRequestDto, alertDistanceRouteEntity);
                this.save(alertDistanceRouteEntity);
                yield alertMapper.mapAlertEntityToAlertResponseDto(alertDistanceRouteEntity);
            }
        };

    }


    public void deleteById(Long alertId) {
        notificationService.deleteByAlertId(alertId);
        alertRepository.deleteById(alertId);
    }


    public ListResponse<AlertShortResponseDto> getAlerts(Long userId, Long vehicleId, Integer page, Integer size, String sort) {
        Specification<AlertEntity> specification = Specification.where(AlertRepository.userIs(userId));
        if(vehicleId != null){
            specification = specification.and(AlertRepository.hasVehicle(vehicleId));
        }
        Page<AlertEntity> result = alertRepository.findAll(specification, PageRequest.of(page, size, Sort.by(Sort.Direction.DESC, sort)));
        ListResponse<AlertShortResponseDto> response = new ListResponse<>();
        response.setItems(result.get().map(alertMapper::mapAlertEntityToAlertShortResponseDto).collect(Collectors.toList()));
        response.setTotal(result.getTotalElements());
        return response;
    }


    public Optional<AlertEntity> getAlertEntity(Long alertId) {
        return alertRepository.findById(alertId);
    }


    public void checkAlerts(PositionEntity newPosition) {
            Specification<AlertEntity> specification = Specification.where(AlertRepository.hasVehicle(newPosition.getRoute().getGps().getVehicle().getId().longValue()));
           List<AlertResponseDto> alerts = alertRepository.findAll(specification).stream().map(alertMapper::mapAlertEntityToAlertResponseDto).collect(Collectors.toList());
           alerts.stream().forEach(a -> {
               switch (a.getType()){
                   case DISTANCE -> checkDistance(a.getId(), newPosition);
                   case ARRIVAL -> checkArrival(a.getId(), newPosition);
                   case SPEED -> checkSpeed(a.getId(), newPosition);
                   case DISTANCE_ROUTE -> checkDistanceRoute(a.getId(), newPosition);
               }
           });


    }

    private void checkDistance(Long id, PositionEntity position){
        alertDistanceRepository.findById(id).ifPresent(
                a ->{
                   BigDecimal distanceKm = BigDecimal.valueOf(Math.acos(Math.sin(a.getPointReferenceLatitude().doubleValue()) * Math.sin(position.getLatitude().doubleValue())
                            + Math.cos(a.getPointReferenceLatitude().doubleValue())* Math.cos(position.getLatitude().doubleValue()) * Math.cos(position.getLongitude().doubleValue() - a.getPointReferenceLongitude().doubleValue()))
                           * 6371);
                   if(a.getMaxDistance().compareTo(distanceKm) < 0){
                       Optional<NotificationEntity> notifications = Optional.empty();
                       if(a.getNotifications() != null){
                            notifications = a.getNotifications().stream().filter( n-> !n.isRead()
                                    && n.getPositions().get(0).getRoute().getGps().getVehicle().getId().equals(position.getRoute().getGps().getVehicle().getId())).reduce(CompareDate:: maxNotificationEntity);
                       }

                       if(!notifications.isEmpty() && !notifications.get().getCreatedDate().isBefore(OffsetDateTime.now().minusMinutes(10))){
                           notificationService.addPositionToNotification(position, notifications.get());
                       }else{
                           NotificationEntity notificationEntity = notificationService.createNotification(a, position);
                           notificationService.addPositionToNotification(position, notificationEntity);
                       }

                   }

                }
        );
    }

    private void checkDistanceRoute(Long id, PositionEntity position){
        alertDistanceRepository.findById(id).ifPresent(
                a ->{
                    if(a.getMaxDistance().compareTo(position.getRoute().getTotalDistance()) < 0){
                        Optional<NotificationEntity> notifications = Optional.empty();
                        if(a.getNotifications() != null){
                            notifications = a.getNotifications().stream().filter( n-> !n.isRead()
                                    && n.getPositions().get(0).getRoute().getGps().getVehicle().getId().equals(position.getRoute().getGps().getVehicle().getId())).reduce(CompareDate:: maxNotificationEntity);
                        }


                        if(!notifications.isEmpty() && !notifications.get().getCreatedDate().isBefore(OffsetDateTime.now().minusMinutes(10))){
                            notificationService.addPositionToNotification(position, notifications.get());
                        }else{
                            NotificationEntity notificationEntity = notificationService.createNotification(a, position);
                            notificationService.addPositionToNotification(position, notificationEntity);
                        }

                    }

                }
        );
    }

    private void checkArrival(Long id, PositionEntity position){
        alertArrivalRepository.findById(id).ifPresent(a ->{
            BigDecimal distanceKm = BigDecimal.valueOf(Math.acos(Math.sin(a.getLatitude().doubleValue()) * Math.sin(position.getLatitude().doubleValue())
                    + Math.cos(a.getLatitude().doubleValue())* Math.cos(position.getLatitude().doubleValue()) * Math.cos(position.getLongitude().doubleValue() - a.getLongitude().doubleValue()))
                    * 6371);
            if(BigDecimal.valueOf(10).compareTo(distanceKm) > 0){
                Optional<NotificationEntity> notifications = Optional.empty();
                if(a.getNotifications() != null){
                    notifications = a.getNotifications().stream().filter( n-> !n.isRead()
                            && n.getPositions().get(0).getRoute().getGps().getVehicle().getId().equals(position.getRoute().getGps().getVehicle().getId())).reduce(CompareDate:: maxNotificationEntity);
                }
                if(!notifications.isEmpty() && !notifications.get().getCreatedDate().isBefore(OffsetDateTime.now().minusMinutes(30))){
                    notificationService.addPositionToNotification(position, notifications.get());
                }else{
                    NotificationEntity notificationEntity = notificationService.createNotification(a, position);
                    notificationService.addPositionToNotification(position, notificationEntity);
                }

            }
        });


    }

    private void checkSpeed(Long id, PositionEntity position){
        alertSpeedRepository.findById(id).ifPresent(a ->{
            if(a.getSpeedLimit().compareTo(position.getSpeed()) < 0){
                Optional<NotificationEntity> notifications = Optional.empty();
                if(a.getNotifications() != null){
                    notifications = a.getNotifications().stream().filter( n-> !n.isRead()
                            && n.getPositions().get(0).getRoute().getGps().getVehicle().getId().equals(position.getRoute().getGps().getVehicle().getId())).reduce(CompareDate:: maxNotificationEntity);
                }
                if(!notifications.isEmpty() && !notifications.get().getCreatedDate().isBefore(OffsetDateTime.now().minusMinutes(5))){
                    notificationService.addPositionToNotification(position, notifications.get());
                }else{
                    NotificationEntity notificationEntity = notificationService.createNotification(a, position);
                    notificationService.addPositionToNotification(position, notificationEntity);
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
        }else if (alertEntity instanceof AlertDistanceRouteEntity){
            alertDistanceRouteRepository.save((AlertDistanceRouteEntity) alertEntity);
        }
    }

    public void deleteByUserId(long id){
        var alerList = alertRepository.findByUser_Id(id);
        for(var alert : alerList){
            this.deleteById(alert.getId());
        }
    }

    public void deleteNotificationsByVehicleId(long id){
        Specification<AlertEntity> specification = Specification.where(AlertRepository.hasVehicle(id));
        var alerList = alertRepository.findAll(specification);
        alerList
            .stream()
            .flatMap( a -> a.getNotifications().stream())
            .filter(n -> n.getPositions().get(0).getRoute().getGps().getVehicle().getId().equals(id))
            .forEach( not ->
                    notificationService.deleteById(not.getId())
            );
    }

}
