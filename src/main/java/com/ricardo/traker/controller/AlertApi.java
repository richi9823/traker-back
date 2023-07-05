package com.ricardo.traker.controller;

import com.ricardo.traker.exception.ServiceException;
import com.ricardo.traker.model.dto.request.AlertRequest.AlertRequestDto;
import com.ricardo.traker.model.dto.request.VehicleRequestDto;
import com.ricardo.traker.model.dto.response.AlertResponse.AlertResponseDto;
import com.ricardo.traker.model.dto.response.VehicleResponseDto;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.util.List;

@RequestMapping("/api/alert")
public interface AlertApi {
    @RequestMapping(value = "", method = RequestMethod.POST)
    ResponseEntity<AlertResponseDto> createAlert(@RequestBody @Valid AlertRequestDto alertRequestDto);


    @RequestMapping(value = "/{alertId}", method = RequestMethod.PUT)
    ResponseEntity<AlertResponseDto> editAlert(@PathVariable Integer alertId, @RequestBody AlertRequestDto vehicleRequestDto);


    @RequestMapping(value = "/{alertId}", method = RequestMethod.DELETE)
    ResponseEntity<?> removeAlert(@PathVariable Integer alertId);

    @RequestMapping(value = "/{vehicleId}", method = RequestMethod.GET)
    ResponseEntity<List<AlertResponseDto>> getVehicleAlerts(Integer vehicleId);
}
