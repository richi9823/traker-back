package com.ricardo.traker.service;

import com.ricardo.traker.enums.IntervalEnum;
import com.ricardo.traker.model.dto.MessageWebSocket;
import com.ricardo.traker.model.dto.response.PositionsResponseDto;

import java.util.List;


public interface PositionService {

    void updatePositions(MessageWebSocket message);

    List<PositionsResponseDto> getPositions(Integer vehicleId, String since, IntervalEnum interval);
}
