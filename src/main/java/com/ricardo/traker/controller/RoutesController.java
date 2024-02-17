package com.ricardo.traker.controller;

import com.ricardo.traker.enums.IntervalEnum;
import com.ricardo.traker.model.dto.response.ListResponse;
import com.ricardo.traker.model.dto.response.RouteResponseDto;
import com.ricardo.traker.model.dto.response.RouteShortResponseDto;
import com.ricardo.traker.service.RouteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;
import java.util.Optional;

@RestController
public class RoutesController implements RoutesApi{

    @Autowired
    RouteService routeService;
    @Override
    public ResponseEntity<ListResponse<RouteShortResponseDto>> getVehicleRoutes(Long vehicleId,Integer page, Integer size, LocalDateTime since, IntervalEnum interval) {
        return  ResponseEntity.ok(routeService.getRoutes(
                vehicleId,
                page,
                size,
                Optional.ofNullable(since).orElse(LocalDateTime.now()),
                Optional.ofNullable(interval).orElse(IntervalEnum._3H)));
    }

    @Override
    public ResponseEntity<RouteResponseDto> getRoute(Long routeId) {
        return ResponseEntity.ok(routeService.getRoute(routeId));
    }

    @Override
    public ResponseEntity<Void> deleteRoute(Long routeId) {
        routeService.deletedById(routeId);
        return ResponseEntity.ok().build();
    }
}
