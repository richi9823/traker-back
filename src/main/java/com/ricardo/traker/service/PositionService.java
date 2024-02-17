package com.ricardo.traker.service;

import com.ricardo.traker.mapper.PositionMapper;
import com.ricardo.traker.model.dto.MessageWebSocket;
import com.ricardo.traker.model.dto.PositionsWebSocket;
import com.ricardo.traker.model.dto.response.PositionsResponseDto;
import com.ricardo.traker.model.entity.GPSEntity;
import com.ricardo.traker.model.entity.PositionEntity;
import com.ricardo.traker.model.entity.RouteEntity;
import com.ricardo.traker.repository.PositionRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@Service
@Slf4j
public class PositionService {
    @Autowired
    PositionRepository positionRepository;

    @Autowired
    PositionMapper positionMapper;

    @Autowired
    AlertService alertService;


    public PositionEntity updatePositions(PositionsWebSocket position, RouteEntity route) {

       PositionEntity positionEntity = positionMapper.mapPositionWebSocketToPositionEntity(position);
       positionEntity.setRoute(route);
       alertService.checkAlerts(positionEntity);

        log.info("Positions updated - device: "
                + position.getDeviceId() + " latitude: "
                + position.getLatitude() + " longitude "
                + position.getLongitude() + " speed: "
                + position.getSpeed() + " date: "
                + position.getServerTime());
       return positionRepository.save(positionEntity);

    }

    public boolean positionExistById(Long id){
        return positionRepository.existsById(id);
    }


    public PositionsResponseDto getPosition(Long vehicleId) {
        PositionEntity pos = positionRepository.findOneByVehicle_IdOrderByTimeDesc(vehicleId).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Last position not found"));
        return  positionMapper.mapPositionEntityToPositionResponse(pos);
    }


    public void deletePositionsByRoute(long routeId) {
        positionRepository.deleteByRoute_id(routeId);
    }


}
