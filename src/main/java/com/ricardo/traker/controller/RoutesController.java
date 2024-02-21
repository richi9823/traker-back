package com.ricardo.traker.controller;

import com.ricardo.traker.model.dto.response.ListResponse;
import com.ricardo.traker.model.dto.response.RouteResponseDto;
import com.ricardo.traker.model.dto.response.RouteShortResponseDto;
import com.ricardo.traker.security.TokenUtils;
import com.ricardo.traker.security.UserDetailsImpl;
import com.ricardo.traker.service.RouteService;
import com.ricardo.traker.service.VehicleService;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDate;

@RestController
public class RoutesController implements RoutesApi{

    @Autowired
    RouteService routeService;

    @Autowired
    VehicleService vehicleService;

    @Autowired
    TokenUtils tokenUtils;

    private final HttpServletRequest request;

    @Autowired
    public RoutesController(HttpServletRequest request){
        this.request = request;
    }

    @Override
    public ResponseEntity<ListResponse<RouteShortResponseDto>> getVehicleRoutes(Long vehicleId, Integer page, Integer size, LocalDate since, LocalDate until) {
        UserDetailsImpl userDetails = tokenUtils.getUser(request);
        if(!vehicleService.getVehicleEntity(vehicleId).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND,"GPS_not found")).getUser().getId().equals(userDetails.getId())){
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }
        return  ResponseEntity.ok(routeService.getRoutes(
                vehicleId,
                page,
                size,
                since,
                until));
    }

    @Override
    public ResponseEntity<RouteResponseDto> getRoute(Long routeId) {
        UserDetailsImpl userDetails = tokenUtils.getUser(request);
        if(!routeService.getRouteEntity(routeId).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND,"Route_not found")).getGps().getVehicle().getUser().getId().equals(userDetails.getId())){
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }
        return ResponseEntity.ok(routeService.getRoute(routeId));
    }

    @Override
    public ResponseEntity<Void> deleteRoute(Long routeId) {
        UserDetailsImpl userDetails = tokenUtils.getUser(request);
        if(!routeService.getRouteEntity(routeId).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND,"Route_not found")).getGps().getVehicle().getUser().getId().equals(userDetails.getId())){
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }
        routeService.deletedById(routeId);
        return ResponseEntity.ok().build();
    }
}
