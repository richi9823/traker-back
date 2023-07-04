package com.ricardo.traker.controller;

import com.ricardo.traker.exception.ServiceException;
import com.ricardo.traker.model.dto.request.VehicleRequest;
import com.ricardo.traker.model.dto.response.VehicleResponseDto;
import com.ricardo.traker.security.TokenUtils;
import com.ricardo.traker.security.UserDetailsImpl;
import com.ricardo.traker.service.VehicleService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class VehicleController implements VehicleApi{

    @Autowired
    TokenUtils tokenUtils;

    @Autowired
    VehicleService vehicleService;


    private final HttpServletRequest request;

    @Autowired
    public VehicleController(HttpServletRequest request){
        this.request = request;
    }



    @Override
    public ResponseEntity<VehicleResponseDto> registerVehicle(@Valid VehicleRequest vehicleRequest) throws ServiceException {
        UserDetailsImpl userDetails = tokenUtils.getUser(request);
        if(userDetails == null) return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();

        return ResponseEntity.ok()
                .body(vehicleService.createVehicle(vehicleRequest, userDetails.getId()));
    }

    @Override
    public ResponseEntity<VehicleResponseDto> editVehicle(Integer vehicleId, VehicleRequest vehicleRequest) {
        UserDetailsImpl userDetails = tokenUtils.getUser(request);
        if(userDetails == null) return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();

        return ResponseEntity.ok()
                .body(vehicleService.editVehicle(vehicleId, vehicleRequest));
    }

    @Override
    public ResponseEntity<VehicleResponseDto> removeVehicle(Integer vehicleId) {
        UserDetailsImpl userDetails = tokenUtils.getUser(request);
        if(userDetails == null) return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();

        return ResponseEntity.ok()
                .body(vehicleService.removeVehicle(vehicleId));
    }

    @Override
    public ResponseEntity<List<VehicleResponseDto>> getUserVehicles() {
        UserDetailsImpl userDetails = tokenUtils.getUser(request);
        if(userDetails == null) return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();

        return ResponseEntity.ok()
                .body(vehicleService.getUserVehicles(userDetails.getId()));
    }


}
