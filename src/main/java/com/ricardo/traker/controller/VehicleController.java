package com.ricardo.traker.controller;

import com.ricardo.traker.exception.ServiceException;
import com.ricardo.traker.model.dto.request.VehicleRequestDto;
import com.ricardo.traker.model.dto.response.ListResponse;
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
import org.springframework.web.server.ResponseStatusException;

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
    public ResponseEntity<VehicleResponseDto> registerVehicle(@Valid VehicleRequestDto vehicleRequestDto) throws ServiceException {
        UserDetailsImpl userDetails = tokenUtils.getUser(request);
        if(userDetails == null) return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();

        return ResponseEntity.ok()
                .body(vehicleService.createVehicle(vehicleRequestDto, userDetails.getId()));
    }

    @Override
    public ResponseEntity<VehicleResponseDto> getVehicle(Integer vehicleId) {
        UserDetailsImpl userDetails = tokenUtils.getUser(request);
        if(!vehicleService.getVehicleEntity(vehicleId).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND,"Vehicle_not found")).getUser().getId().equals(userDetails.getId())){
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }
        return ResponseEntity.ok()
                .body(vehicleService.getVehicle(vehicleId));
    }

    @Override
    public ResponseEntity<VehicleResponseDto> editVehicle(Integer vehicleId, VehicleRequestDto vehicleRequestDto) {
        UserDetailsImpl userDetails = tokenUtils.getUser(request);
        if(userDetails == null) return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();

        return ResponseEntity.ok()
                .body(vehicleService.editVehicle(vehicleId, vehicleRequestDto));
    }

    @Override
    public ResponseEntity<VehicleResponseDto> removeVehicle(Integer vehicleId) {
        UserDetailsImpl userDetails = tokenUtils.getUser(request);
        if(userDetails == null) return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();

        return ResponseEntity.ok()
                .body(vehicleService.removeVehicle(vehicleId));
    }

    @Override
    public ResponseEntity<ListResponse<VehicleResponseDto>> getUserVehicles(Integer page, Integer size, String sort) {
        UserDetailsImpl userDetails = tokenUtils.getUser(request);
        if(userDetails == null) return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();

        return ResponseEntity.ok()
                .body(vehicleService.getUserVehicles(userDetails.getId(), page, size, sort));
    }


}
