package com.ricardo.traker.service;

import com.ricardo.traker.mapper.PositionMapper;
import com.ricardo.traker.model.dto.MessageWebSocket;
import com.ricardo.traker.model.dto.response.PositionsResponseDto;
import com.ricardo.traker.model.entity.GPSEntity;
import com.ricardo.traker.model.entity.PositionEntity;
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


    public void updatePositions(MessageWebSocket message, GPSEntity g) {
       message.getPositions().stream().forEach(p -> {
           if(!positionRepository.existsById(p.getId())){
                   PositionEntity positionEntity = positionMapper.mapPositionWebSocketToPositionEntity(p);
                   //positionEntity.setGps(g);
                   alertService.checkAlerts(positionEntity);
                   positionRepository.save(positionEntity);
                   log.info("Positions updated - device: "
                           + p.getDeviceId() + " latitude: "
                           + p.getLatitude() + " longitude "
                           + p.getLongitude() + " speed: "
                           + p.getSpeed() + " date: "
                           + p.getServerTime());

           }
       });

    }


    public PositionsResponseDto getPosition(Long vehicleId) {
        Specification<PositionEntity> specification = Specification.where(PositionRepository.hasVehicle(vehicleId));
        List<PositionEntity> list = positionRepository.findAll(specification, Sort.by(Sort.Direction.DESC, "time"));
        if(list.isEmpty()){
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Not has positions");
        }else{
            return  positionMapper.mapPositionEntityToPositionResponse(list.get(0));
        }
    }


    public void deletePositionsByRoute(long routeId) {
        positionRepository.deleteByRoute_id(routeId);
    }


}
