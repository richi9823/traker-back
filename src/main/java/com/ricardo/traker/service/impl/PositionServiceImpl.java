package com.ricardo.traker.service.impl;

import com.ricardo.traker.enums.IntervalEnum;
import com.ricardo.traker.mapper.PositionMapper;
import com.ricardo.traker.model.dto.MessageWebSocket;
import com.ricardo.traker.model.dto.response.PositionsResponseDto;
import com.ricardo.traker.model.entity.NotificationEntity;
import com.ricardo.traker.model.entity.PositionEntity;
import com.ricardo.traker.repository.PositionRepository;
import com.ricardo.traker.service.AlertService;
import com.ricardo.traker.service.GPSService;
import com.ricardo.traker.service.PositionService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@Slf4j
public class PositionServiceImpl implements PositionService {
    @Autowired
    PositionRepository positionRepository;

    @Autowired
    GPSService gpsService;

    @Autowired
    PositionMapper positionMapper;

    @Autowired
    AlertService alertService;


    public void updatePositions(MessageWebSocket message) {
        if(message.getPositions() != null){
            message.getPositions().stream().forEach(p -> {
                if(!positionRepository.existsById(p.getId())){
                    gpsService.getGPSEntity(p.getDeviceId()).ifPresent( g -> {
                        PositionEntity positionEntity = positionMapper.mapPositionWebSocketToPositionEntity(p);
                        positionEntity.setGps(g);
                        alertService.checkAlerts(positionEntity);
                        positionRepository.save(positionEntity);
                        log.info("position saved");
                        gpsService.updateGPSByPosition(p);

                    });
                }
            });
        }
    }

    @Override
    public List<PositionsResponseDto> getPositions(Integer vehicleId, String since, IntervalEnum interval) {
        Specification<PositionEntity> specification = Specification.where(PositionRepository.hasVehicle(vehicleId))
                .and(PositionRepository.hasInterval(since, interval));
        return positionRepository.findAll(specification).stream().map(positionMapper::mapPositionEntityToPositionResponse).collect(Collectors.toList());
    }

    private Optional<PositionEntity> getLastPositionsByGpsId(Integer gpsId){

        List<PositionEntity> positions = positionRepository.findByGps_TraccarDeviceIdOrderByTimeDesc(gpsId);
        if(positions.isEmpty()){
            return Optional.empty();
        }else{
            return Optional.of(positions.get(0));
        }
    }
}
