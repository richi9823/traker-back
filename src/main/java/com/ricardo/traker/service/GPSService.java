package com.ricardo.traker.service;

import com.ricardo.traker.exception.ServiceException;
import com.ricardo.traker.model.dto.request.VehicleRequest;
import com.ricardo.traker.model.dto.response.VehicleResponseDto;
import com.ricardo.traker.model.entity.GPSEntity;

import java.util.Optional;

public interface GPSService {

    GPSEntity createGPS(VehicleRequest vehicleRequest) throws ServiceException;


    Optional<GPSEntity> getGPSEntity(Integer gpsId);
}
