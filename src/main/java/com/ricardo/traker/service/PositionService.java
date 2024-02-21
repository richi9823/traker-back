package com.ricardo.traker.service;

import com.ricardo.traker.mapper.PositionMapper;
import com.ricardo.traker.model.dto.PositionsWebSocket;
import com.ricardo.traker.model.dto.response.FullPositionResponseDto;
import com.ricardo.traker.model.dto.response.PositionResponseDto;
import com.ricardo.traker.model.entity.PositionEntity;
import com.ricardo.traker.model.entity.RouteEntity;
import com.ricardo.traker.repository.PositionRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
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


    public FullPositionResponseDto getPosition(Long vehicleId) {
        List<PositionEntity> pos = positionRepository.findOneByRoute_Gps_Vehicle_IdOrderByTimeDesc(vehicleId);
        if(pos.isEmpty()){
            throw  new ResponseStatusException(HttpStatus.NOT_FOUND, "Last position not found");
        }else{
            return  positionMapper.mapPositionEntityToFullPositionResponse(pos.get(0));
        }
    }


    public void deletePositionsByRoute(long routeId) {
        positionRepository.deleteByRoute_id(routeId);
    }


}
