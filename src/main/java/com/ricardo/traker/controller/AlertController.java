package com.ricardo.traker.controller;

import com.ricardo.traker.exception.ServiceException;
import com.ricardo.traker.model.dto.request.AlertRequest.AlertRequestDto;
import com.ricardo.traker.model.dto.response.AlertResponse.AlertResponseDto;
import com.ricardo.traker.model.dto.response.ListResponse;
import com.ricardo.traker.security.TokenUtils;
import com.ricardo.traker.security.UserDetailsImpl;
import com.ricardo.traker.service.AlertService;
import com.ricardo.traker.service.VehicleService;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class AlertController implements AlertApi{

    @Autowired
    TokenUtils tokenUtils;

    @Autowired
    AlertService alertService;


    private final HttpServletRequest request;

    @Autowired
    public AlertController(HttpServletRequest request){
        this.request = request;
    }


    @Override
    public ResponseEntity<AlertResponseDto> createAlert(AlertRequestDto alertRequestDto){
        UserDetailsImpl userDetails = tokenUtils.getUser(request);
        if(userDetails == null) return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();

        return ResponseEntity.ok()
                .body(alertService.createAlert(alertRequestDto));
    }

    @Override
    public ResponseEntity<AlertResponseDto> editAlert(Long alertId, AlertRequestDto alertRequestDto) {
        UserDetailsImpl userDetails = tokenUtils.getUser(request);
        if(userDetails == null) return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();

        return ResponseEntity.ok()
                .body(alertService.editAlert(alertId, alertRequestDto));
    }

    @Override
    public ResponseEntity<AlertResponseDto> getAlert(Long alertId) {
        UserDetailsImpl userDetails = tokenUtils.getUser(request);
        if(userDetails == null) return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();

        return ResponseEntity.ok()
                .body(alertService.getAlert(alertId));
    }

    @Override
    public ResponseEntity<?> removeAlert(Long alertId) {
        UserDetailsImpl userDetails = tokenUtils.getUser(request);
        if(userDetails == null) return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        alertService.removeAlert(alertId);
        return ResponseEntity.ok().build();
    }

    @Override
    public ResponseEntity<ListResponse<AlertResponseDto>> getVehicleAlerts(Long vehicleId, Integer page, Integer size, String sort) {
        UserDetailsImpl userDetails = tokenUtils.getUser(request);
        if(userDetails == null) return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        return ResponseEntity.ok()
                .body(alertService.getVehicleAlerts(vehicleId, page, size, sort));
    }
}
