package com.ricardo.traker.service;

import com.ricardo.traker.enums.IntervalEnum;
import com.ricardo.traker.model.dto.MessageWebSocket;
import com.ricardo.traker.model.dto.PositionsWebSocket;
import com.ricardo.traker.model.dto.response.ListResponse;
import com.ricardo.traker.model.dto.response.RouteResponseDto;
import com.ricardo.traker.model.dto.response.RouteShortResponseDto;
import com.ricardo.traker.mapper.*;
import com.ricardo.traker.model.entity.GPSEntity;
import com.ricardo.traker.model.entity.PositionEntity;
import com.ricardo.traker.model.entity.RouteEntity;
import com.ricardo.traker.repository.RouteRepository;
import com.ricardo.traker.util.CompareDate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.OffsetDateTime;
import java.time.ZoneOffset;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class RouteService {

    @Autowired
    RouteRepository routeRepository;

    @Autowired
    PositionService positionService;

    @Autowired
    RouteMapper routeMapper;

    @Transactional
    public void updateRoutes(PositionsWebSocket position, GPSEntity gps){
        if(!positionService.positionExistById(position.getId()) && (gps.getMotion() || gps.getActualDistance().compareTo(BigDecimal.valueOf(20L)) > 0)){
            Optional<RouteEntity> lastRoute = routeRepository.findOneByGps_TraccarDeviceIdAndFinishIsNullOrderByStartDesc(gps.getTraccarDeviceId());
            lastRoute.ifPresentOrElse( r ->{
                if(r.getPositions() != null){
                    Optional<PositionEntity> lastPosition = r.getPositions().stream().reduce(CompareDate::maxPosition);
                    lastPosition.ifPresent(p->{
                        if(p.getTime().plusMinutes(30L).isBefore(position.getServerTime().toInstant().atOffset(ZoneOffset.UTC))){
                            r.setFinish(p.getTime());
                            routeRepository.save(r);
                            positionService.updatePositions(position, this.createRoute(gps));
                        }else{
                            positionService.updatePositions(position, r);
                        }
                    });
                }else{
                    positionService.updatePositions(position, r);
                }

            }, () ->  positionService.updatePositions(position, this.createRoute(gps)));
        }


    }

    private RouteEntity createRoute(GPSEntity gps){
        return routeRepository.save(
                RouteEntity.builder()
                        .start(OffsetDateTime.now())
                        .gps(gps)
                        .build()
        );
    }

    public void deleteByGpsId(long id){
        var routes = routeRepository.findByGps_TraccarDeviceId(id);
        for(var r: routes){
            this.deletedById(r.getId());
        }
    }

    public void deletedById(long id){
        positionService.deletePositionsByRoute(id);
        routeRepository.deleteById(id);
    }

    public ListResponse<RouteShortResponseDto> getRoutes(Long vehicleId, Integer page, Integer size, LocalDateTime since, IntervalEnum interval) {
        Specification<RouteEntity> specification = Specification.where(RouteRepository.hasVehicle(vehicleId))
                .and(RouteRepository.hasInterval(since, interval));
        Page<RouteEntity> result = routeRepository.findAll(specification, PageRequest.of(page, size, Sort.by(Sort.Direction.DESC, "start")));
        ListResponse<RouteShortResponseDto> response = new ListResponse<>();
        response.setItems(result.get().map(routeMapper::mapEntityToShortResponse)
                .collect(Collectors.toList()));
        response.setTotal(result.getTotalElements());
        return response;
    }

    public RouteResponseDto getRoute(Long id) {
        return routeRepository.findById(id).map(routeMapper::mapEntityToResponse).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Route not found - " + id));
    }
}
