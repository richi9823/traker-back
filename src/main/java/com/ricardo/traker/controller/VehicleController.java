package com.ricardo.traker.controller;

import com.ricardo.traker.exception.ServiceException;
import com.ricardo.traker.model.dto.request.GPSDeviceRequestDto;
import com.ricardo.traker.model.dto.request.VehicleRequestDto;
import com.ricardo.traker.model.dto.response.ListResponse;
import com.ricardo.traker.model.dto.response.VehicleResponseDto;
import com.ricardo.traker.model.dto.response.VehicleShortResponseDto;
import com.ricardo.traker.security.TokenUtils;
import com.ricardo.traker.security.UserDetailsImpl;
import com.ricardo.traker.service.VehicleService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
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
    public ResponseEntity<VehicleResponseDto> getVehicle(Long vehicleId) {
        UserDetailsImpl userDetails = tokenUtils.getUser(request);
        if(!vehicleService.getVehicleEntity(vehicleId).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND,"Vehicle_not found")).getUser().getId().equals(userDetails.getId())){
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }
        return ResponseEntity.ok()
                .body(vehicleService.getVehicle(vehicleId));
    }

    @Override
    public ResponseEntity<VehicleResponseDto> editVehicle(Long vehicleId, VehicleRequestDto vehicleRequestDto) throws ServiceException {
        UserDetailsImpl userDetails = tokenUtils.getUser(request);
        if(userDetails == null) return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();

        return ResponseEntity.ok()
                .body(vehicleService.editVehicle(vehicleId, vehicleRequestDto));
    }

    @Override
    public ResponseEntity<Void> removeVehicle(Long vehicleId) {
        UserDetailsImpl userDetails = tokenUtils.getUser(request);
        if(userDetails == null) return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        vehicleService.deleteById(vehicleId);
        return ResponseEntity.ok().build();
    }

    @Override
    public ResponseEntity<ListResponse<VehicleShortResponseDto>> getUserVehicles(Integer page, Integer size, String sort) {
        UserDetailsImpl userDetails = tokenUtils.getUser(request);
        if(userDetails == null) return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();

        return ResponseEntity.ok()
                .body(vehicleService.getUserVehicles(userDetails.getId(), page, size, sort));
    }

    @Override
    public ResponseEntity<VehicleResponseDto> setImage(Long vehicleId, MultipartFile image) throws ServiceException {
        return ResponseEntity.ok()
                .body(vehicleService.setImage(vehicleId, image));
    }

    @Override
    public ResponseEntity<VehicleResponseDto> deleteImage(Long vehicleId) {
        return ResponseEntity.ok()
                .body(vehicleService.deleteImage(vehicleId));
    }

    @Override
    public ResponseEntity<VehicleResponseDto> addGPSDevice(Long vehicleId, GPSDeviceRequestDto gpsDeviceRequestDto) throws ServiceException {
        return ResponseEntity.ok()
                .body(vehicleService.addGpsDevice(vehicleId, gpsDeviceRequestDto));
    }


}
