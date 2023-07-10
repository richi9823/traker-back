package com.ricardo.traker.service.impl;

import com.ricardo.traker.mapper.PositionMapper;
import com.ricardo.traker.model.dto.MessageWebSocket;
import com.ricardo.traker.model.entity.PositionEntity;
import com.ricardo.traker.repository.PositionRepository;
import com.ricardo.traker.service.GPSService;
import com.ricardo.traker.service.PositionService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class PositionServiceImpl implements PositionService {
    @Autowired
    PositionRepository positionRepository;

    @Autowired
    GPSService gpsService;

    @Autowired
    PositionMapper positionMapper;


    public void updatePositions(MessageWebSocket message) {
        if(message.getPositions() != null){
            message.getPositions().stream().forEach(p -> {
                if(!positionRepository.existsById(p.getId())){
                    gpsService.getGPSEntity(p.getDeviceId()).ifPresent( g -> {
                        PositionEntity positionEntity = positionMapper.mapPositionWebSocketToPositionEntity(p);
                        positionEntity.setGps(g);
                        positionRepository.save(positionEntity);
                        log.info("position saved");
                        gpsService.updateGPSByPosition(p);
                    });
                }
            });
        }
    }
}
