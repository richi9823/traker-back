package com.ricardo.traker.service;

import com.ricardo.traker.enums.IntervalEnum;
import com.ricardo.traker.model.dto.MessageWebSocket;
import com.ricardo.traker.model.dto.response.ListResponse;
import com.ricardo.traker.model.dto.response.RouteResponseDto;
import com.ricardo.traker.model.dto.response.RouteShortResponseDto;
import com.ricardo.traker.mapper.*;
import com.ricardo.traker.model.entity.GPSEntity;
import com.ricardo.traker.model.entity.RouteEntity;
import com.ricardo.traker.repository.RouteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDateTime;
import java.util.stream.Collectors;

@Service
public class RouteService {

    @Autowired
    RouteRepository routeRepository;

    @Autowired
    PositionService positionService;

    @Autowired
    RouteMapper routeMapper;

    public void updateRoutes(MessageWebSocket message, GPSEntity gps){
        positionService.updatePositions(message, gps);
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
