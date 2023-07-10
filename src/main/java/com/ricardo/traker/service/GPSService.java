package com.ricardo.traker.service;

import com.ricardo.traker.exception.ServiceException;
import com.ricardo.traker.model.dto.MessageWebSocket;
import com.ricardo.traker.model.dto.PositionsWebSocket;
import com.ricardo.traker.model.dto.request.VehicleRequestDto;
import com.ricardo.traker.model.entity.GPSEntity;

import java.util.Optional;

public interface GPSService {

    GPSEntity createGPS(VehicleRequestDto vehicleRequestDto) throws ServiceException;


    Optional<GPSEntity> getGPSEntity(Integer gpsId);

    void updateGPSByWebSocket(MessageWebSocket message);

    void updateGPSByPosition(PositionsWebSocket message);
}
