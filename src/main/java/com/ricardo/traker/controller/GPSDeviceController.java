package com.ricardo.traker.controller;


import com.ricardo.traker.enums.GPSStatusEnum;
import com.ricardo.traker.model.dto.response.GPSResponseDto;
import com.ricardo.traker.model.dto.response.GPSShortResponseDto;
import com.ricardo.traker.service.GPSService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class GPSDeviceController implements GPSDeviceApi {

    @Autowired
    GPSService gpsService;



    @Override
    public ResponseEntity<GPSResponseDto> updateStatusGPS(Long gpsId, GPSStatusEnum status) {
        return ResponseEntity.ok(gpsService.updateGPSStatus(gpsId, status));
    }

    @Override
    public ResponseEntity<Void> deleteGPSDevice(Long gpsId) {
        gpsService.deleteById(gpsId);
        return ResponseEntity.ok().build();
    }

    @Override
    public ResponseEntity<GPSResponseDto> getGPSDevice(Long gpsId) {
        return ResponseEntity.ok(gpsService.getGPS(gpsId));
    }

    @Override
    public ResponseEntity<List<GPSShortResponseDto>> getListGPS(Long vehicleId) {
        return ResponseEntity.ok(gpsService.getListGPS(vehicleId));
    }
}
