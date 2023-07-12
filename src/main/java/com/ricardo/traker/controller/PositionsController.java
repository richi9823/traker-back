package com.ricardo.traker.controller;

import com.ricardo.traker.enums.IntervalEnum;
import com.ricardo.traker.model.dto.response.PositionsResponseDto;
import com.ricardo.traker.security.TokenUtils;
import com.ricardo.traker.security.UserDetailsImpl;
import com.ricardo.traker.service.PositionService;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

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
    public ResponseEntity<List<PositionsResponseDto>> getVehiclePositions(Integer vehicleId, String since, IntervalEnum interval) {
        UserDetailsImpl userDetails = tokenUtils.getUser(request);
        if(userDetails == null) return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        return ResponseEntity.ok(
                positionService.getPositions(vehicleId,
                        Optional.ofNullable(since).orElse(LocalDateTime.now().toString()),
                        Optional.ofNullable(interval).orElse(IntervalEnum._3H))
        );
    }
}
