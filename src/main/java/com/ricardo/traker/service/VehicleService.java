package com.ricardo.traker.service;

import com.ricardo.traker.exception.ServiceException;
import com.ricardo.traker.model.dto.MessageWebSocket;
import com.ricardo.traker.model.dto.request.VehicleRequestDto;
import com.ricardo.traker.model.dto.response.ListResponse;
import com.ricardo.traker.model.dto.response.VehicleResponseDto;
import com.ricardo.traker.model.entity.VehicleEntity;

import java.util.List;
import java.util.Optional;

public interface VehicleService {

    VehicleResponseDto createVehicle(VehicleRequestDto vehicleRequestDto, Integer userId) throws ServiceException;

    VehicleResponseDto editVehicle(Integer vehicleId, VehicleRequestDto vehicleRequestDto);

    VehicleResponseDto getVehicle(Integer vehicleId);

    VehicleResponseDto removeVehicle(Integer vehicleId);

    ListResponse<VehicleResponseDto> getUserVehicles(Integer userId, Integer page, Integer size, String sort);


    Optional<VehicleEntity> getVehicleEntity(Integer vehicleId);

    Optional<VehicleEntity> getVehicleEntityByGpsId(Integer gpsId);
}
