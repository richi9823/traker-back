package com.ricardo.traker.controller;

import com.ricardo.traker.model.dto.response.PositionsResponseDto;
import com.ricardo.traker.security.TokenUtils;
import com.ricardo.traker.security.UserDetailsImpl;
import com.ricardo.traker.service.PositionService;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class PositionsController implements PositionsApi{

    @Autowired
    PositionService positionService;

    @Autowired
    TokenUtils tokenUtils;

    private final HttpServletRequest request;

    @Autowired
    public PositionsController(HttpServletRequest request){
        this.request = request;
    }
    @Override
    public ResponseEntity<PositionsResponseDto> getVehiclePosition(Long vehicleId) {
        UserDetailsImpl userDetails = tokenUtils.getUser(request);
        if(userDetails == null) return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        return ResponseEntity.ok(
                positionService.getPosition(vehicleId)
        );
    }


}
