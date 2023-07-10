package com.ricardo.traker.service;

import com.ricardo.traker.model.dto.MessageWebSocket;


public interface PositionService {

    void updatePositions(MessageWebSocket message);
}
