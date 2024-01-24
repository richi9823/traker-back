package com.ricardo.traker.service;

import com.ricardo.traker.enums.IntervalEnum;
import com.ricardo.traker.model.dto.MessageWebSocket;
import com.ricardo.traker.model.dto.response.PositionsResponseDto;
import com.ricardo.traker.model.dto.response.RoutesResponseDto;

import java.time.LocalDateTime;
import java.util.List;


public interface PositionService {

    void updatePositions(MessageWebSocket message);

    PositionsResponseDto getPosition(Integer vehicleId);

    List<RoutesResponseDto> getRoutes(Integer vehicleId, LocalDateTime since, IntervalEnum interval);
}
