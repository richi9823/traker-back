package com.ricardo.traker.service.impl;

import com.ricardo.traker.mapper.AlertMapper;
import com.ricardo.traker.model.dto.request.AlertRequest.AlertRequestDto;
import com.ricardo.traker.model.dto.response.AlertResponse.AlertResponseDto;
import com.ricardo.traker.model.entity.AlertEntity.AlertArrivalEntity;
import com.ricardo.traker.model.entity.AlertEntity.AlertDistanceEntity;
import com.ricardo.traker.model.entity.AlertEntity.AlertEntity;
import com.ricardo.traker.model.entity.AlertEntity.AlertSpeedEntity;
import com.ricardo.traker.model.entity.VehicleEntity;
import com.ricardo.traker.repository.AlertArrivalRepository;
import com.ricardo.traker.repository.AlertDistanceRepository;
import com.ricardo.traker.repository.AlertRepository;
import com.ricardo.traker.repository.AlertSpeedRepository;
import com.ricardo.traker.service.AlertService;
import com.ricardo.traker.service.VehicleService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

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


    @Override
    public AlertResponseDto createAlert(AlertRequestDto alertRequestDto) {
        VehicleEntity vehicleEntity = vehicleService.getVehicleEntity(alertRequestDto.getVehicleId()).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "vehicle not found"));
        AlertEntity alertEntity = alertMapper.mapAlertRequestDtoToAlertEntity(alertRequestDto);
        alertEntity.setVehicle(vehicleEntity);
        this.save(alertEntity);
        return alertMapper.mapAlertEntityToAlertResponseDto(alertEntity);
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
    public Optional<AlertEntity> getAlertEntity(Integer alertId) {
        return alertRepository.findById(alertId);
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
