package com.ricardo.traker.service;

import com.ricardo.traker.enums.IntervalEnum;
import com.ricardo.traker.mapper.PositionMapper;
import com.ricardo.traker.model.dto.MessageWebSocket;
import com.ricardo.traker.model.dto.response.PositionsResponseDto;
import com.ricardo.traker.model.dto.response.RoutesResponseDto;
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

import java.time.LocalDateTime;
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


    public List<RoutesResponseDto> getRoutes(Long vehicleId, LocalDateTime since, IntervalEnum interval) {
        /*Specification<PositionEntity> specification = Specification.where(PositionRepository.hasVehicle(vehicleId))
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
        return routes;*/
        return null;
    }


    public void deletePositionsByRoute(long routeId) {
        positionRepository.deleteByRoute_id(routeId);
    }

    public void deletePosition(long positionId) {
        positionRepository.deleteById(positionId);
    }


}
