package com.ricardo.traker.controller;

import com.ricardo.traker.model.dto.response.FullPositionResponseDto;
import com.ricardo.traker.model.dto.response.PositionResponseDto;
import com.ricardo.traker.security.TokenUtils;
import com.ricardo.traker.security.UserDetailsImpl;
import com.ricardo.traker.service.PositionService;
import com.ricardo.traker.service.VehicleService;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

@RestController
public class PositionsController implements PositionsApi{

    @Autowired
    PositionService positionService;

    @Autowired
    TokenUtils tokenUtils;

    @Autowired
    VehicleService vehicleService;

    private final HttpServletRequest request;

    @Autowired
    public PositionsController(HttpServletRequest request){
        this.request = request;
    }
    @Override
    public FullPositionResponseDto getLastPosition(Long vehicleId){
        UserDetailsImpl userDetails = tokenUtils.getUser(request);
        if(!vehicleService.getVehicleEntity(vehicleId).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND,"Vehicle_not found")).getUser().getId().equals(userDetails.getId())){
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED,"Unhauthorized");
        }
        return positionService.getPosition(vehicleId);
    }


}
