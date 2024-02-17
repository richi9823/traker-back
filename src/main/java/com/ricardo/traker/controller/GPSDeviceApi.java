package com.ricardo.traker.controller;

import com.ricardo.traker.enums.GPSStatusEnum;
import com.ricardo.traker.exception.ServiceException;
import com.ricardo.traker.model.dto.request.GPSDeviceRequestDto;
import com.ricardo.traker.model.dto.response.GPSResponseDto;
import com.ricardo.traker.model.dto.response.GPSShortResponseDto;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@RequestMapping("/api/gps")
@SecurityRequirement(name = "Bearer Authentication")
public interface GPSDeviceApi {

    @RequestMapping(value = "/{gpsId}", method = RequestMethod.PUT)
    ResponseEntity<GPSResponseDto> updateStatusGPS(@PathVariable Long gpsId, @RequestParam(name = "status", required = true) GPSStatusEnum status);

    @RequestMapping(value = "/{gpsId}", method = RequestMethod.DELETE)
    ResponseEntity<Void> deleteGPSDevice(@PathVariable Long gpsId);

    @RequestMapping(value = "/{gpsId}", method = RequestMethod.GET)
    ResponseEntity<GPSResponseDto> getGPSDevice(@PathVariable Long gpsId);

    @RequestMapping(value = "/vehicle/{vehicleId}", method = RequestMethod.POST)
    ResponseEntity<List<GPSShortResponseDto>> getListGPS(@PathVariable Long vehicleId);
}
