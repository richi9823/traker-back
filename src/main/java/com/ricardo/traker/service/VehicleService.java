package com.ricardo.traker.service;

import com.ricardo.traker.exception.ServiceException;
import com.ricardo.traker.model.dto.request.VehicleRequestDto;
import com.ricardo.traker.model.dto.response.VehicleResponseDto;
import com.ricardo.traker.model.entity.VehicleEntity;

import java.util.List;
import java.util.Optional;

public interface VehicleService {

    VehicleResponseDto createVehicle(VehicleRequestDto vehicleRequestDto, Integer userId) throws ServiceException;

    VehicleResponseDto editVehicle(Integer vehicleId, VehicleRequestDto vehicleRequestDto);

    VehicleResponseDto removeVehicle(Integer vehicleId);

    List<VehicleResponseDto> getUserVehicles(Integer userId);


    Optional<VehicleEntity> getVehicleEntity(Integer vehicleId);
}
