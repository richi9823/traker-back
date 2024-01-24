package com.ricardo.traker.service.impl;

import com.ricardo.traker.enums.IntervalEnum;
import com.ricardo.traker.mapper.PositionMapper;
import com.ricardo.traker.model.dto.MessageWebSocket;
import com.ricardo.traker.model.dto.response.PositionsResponseDto;
import com.ricardo.traker.model.dto.response.RoutesResponseDto;
import com.ricardo.traker.model.entity.NotificationEntity;
import com.ricardo.traker.model.entity.PositionEntity;
import com.ricardo.traker.repository.PositionRepository;
import com.ricardo.traker.service.AlertService;
import com.ricardo.traker.service.GPSService;
import com.ricardo.traker.service.PositionService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDateTime;
import java.util.ArrayList;
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
                        log.info("Positions updated - device: "
                                + p.getDeviceId() + " latitude: "
                                + p.getLatitude() + " longitude "
                                + p.getLongitude() + " speed: "
                                + p.getSpeed() + " date: "
                                + p.getServerTime());
                        gpsService.updateGPSByPosition(p);

                    });
                }
            });
        }
    }

    @Override
    public PositionsResponseDto getPosition(Integer vehicleId) {
        Specification<PositionEntity> specification = Specification.where(PositionRepository.hasVehicle(vehicleId));
        List<PositionEntity> list = positionRepository.findAll(specification, Sort.by(Sort.Direction.DESC, "time"));
        if(list.isEmpty()){
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Not has positions");
        }else{
            return  positionMapper.mapPositionEntityToPositionResponse(list.get(0));
        }
    }

    @Override
    public List<RoutesResponseDto> getRoutes(Integer vehicleId, LocalDateTime since, IntervalEnum interval) {
        Specification<PositionEntity> specification = Specification.where(PositionRepository.hasVehicle(vehicleId))
                .and(PositionRepository.hasInterval(since, interval));
        List<PositionEntity> list = positionRepository.findAll(specification, Sort.by(Sort.Direction.DESC, "time"));
        List<RoutesResponseDto> routes = new ArrayList<>();
        RoutesResponseDto lastRoute = RoutesResponseDto.builder()
                .positions(List.of())
                .finish(list.get(0).getTime())
                .init(list.get(0).getTime())
                .build();
        for(PositionEntity p : list){
            if(p.getTime().isBefore(lastRoute.getInit().plusMinutes(10))){
                lastRoute.getPositions().add(positionMapper.mapPositionEntityToPositionResponse(p));
                lastRoute.setInit(p.getTime());
            }else{
                routes.add(lastRoute);
                lastRoute = RoutesResponseDto.builder()
                        .positions(List.of(positionMapper.mapPositionEntityToPositionResponse(p)))
                        .finish(p.getTime())
                        .init(p.getTime())
                        .build();
            }
        }
        routes.add(lastRoute);
        //TODO: Hacer esta clasificacion en el modelo d edatos, posiciones rrelacionadas a rutas
        return routes;
    }


}
