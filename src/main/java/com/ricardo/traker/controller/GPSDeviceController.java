package com.ricardo.traker.controller;


import com.ricardo.traker.enums.GPSStatusEnum;
import com.ricardo.traker.model.dto.response.GPSResponseDto;
import com.ricardo.traker.model.dto.response.GPSShortResponseDto;
import com.ricardo.traker.security.TokenUtils;
import com.ricardo.traker.security.UserDetailsImpl;
import com.ricardo.traker.service.GPSService;
import com.ricardo.traker.service.VehicleService;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@RestController
public class GPSDeviceController implements GPSDeviceApi {

    @Autowired
    GPSService gpsService;

    @Autowired
    VehicleService vehicleService;

    @Autowired
    TokenUtils tokenUtils;

    private final HttpServletRequest request;

    @Autowired
    public GPSDeviceController(HttpServletRequest request){
        this.request = request;
    }

    @Override
    public ResponseEntity<GPSResponseDto> updateStatusGPS(Long gpsId, GPSStatusEnum status) {
        UserDetailsImpl userDetails = tokenUtils.getUser(request);
        if(!gpsService.getGpsEntity(gpsId).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND,"GPS_not found")).getVehicle().getUser().getId().equals(userDetails.getId())){
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }
        return ResponseEntity.ok(gpsService.updateGPSStatus(gpsId, status));
    }

    @Override
    public ResponseEntity<Void> deleteGPSDevice(Long gpsId) {
        UserDetailsImpl userDetails = tokenUtils.getUser(request);
        if(!gpsService.getGpsEntity(gpsId).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND,"GPS_not found")).getVehicle().getUser().getId().equals(userDetails.getId())){
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }
        gpsService.deleteById(gpsId);
        return ResponseEntity.ok().build();
    }

    @Override
    public ResponseEntity<GPSResponseDto> getGPSDevice(Long gpsId) {
        UserDetailsImpl userDetails = tokenUtils.getUser(request);
        if(!gpsService.getGpsEntity(gpsId).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND,"GPS_not found")).getVehicle().getUser().getId().equals(userDetails.getId())){
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }
        return ResponseEntity.ok(gpsService.getGPS(gpsId));
    }

    @Override
    public ResponseEntity<List<GPSShortResponseDto>> getListGPS(Long vehicleId) {
        UserDetailsImpl userDetails = tokenUtils.getUser(request);
        if(!vehicleService.getVehicleEntity(vehicleId).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND,"Vehicle_not found")).getUser().getId().equals(userDetails.getId())){
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }
        return ResponseEntity.ok(gpsService.getListGPS(vehicleId));
    }
}
