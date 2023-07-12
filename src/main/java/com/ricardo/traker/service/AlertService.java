package com.ricardo.traker.service;

import com.ricardo.traker.exception.ServiceException;
import com.ricardo.traker.model.dto.request.AlertRequest.AlertRequestDto;
import com.ricardo.traker.model.dto.request.VehicleRequestDto;
import com.ricardo.traker.model.dto.response.AlertResponse.AlertResponseDto;
import com.ricardo.traker.model.dto.response.VehicleResponseDto;
import com.ricardo.traker.model.entity.AlertEntity.AlertEntity;
import com.ricardo.traker.model.entity.NotificationEntity;
import com.ricardo.traker.model.entity.PositionEntity;

import java.util.List;
import java.util.Optional;

public interface AlertService {

    AlertResponseDto createAlert(AlertRequestDto alertRequestDto);

    AlertResponseDto editAlert(Integer alertId, AlertRequestDto alertRequestDto);

    void removeAlert(Integer alertId);

    List<AlertResponseDto> getVehicleAlerts(Integer vehicleId);

    Optional<AlertEntity> getAlertEntity(Integer alertId);

    void checkAlerts(PositionEntity newPosition);
}
