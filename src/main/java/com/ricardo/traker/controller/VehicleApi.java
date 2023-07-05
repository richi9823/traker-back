package com.ricardo.traker.controller;

import com.ricardo.traker.exception.ServiceException;
import com.ricardo.traker.model.dto.request.VehicleRequestDto;

import com.ricardo.traker.model.dto.response.VehicleResponseDto;

import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequestMapping("/api/vehicle")
public interface VehicleApi {


    @RequestMapping(value = "", method = RequestMethod.POST)
    ResponseEntity<VehicleResponseDto> registerVehicle(@RequestBody @Valid VehicleRequestDto vehicleRequestDto) throws ServiceException;


    @RequestMapping(value = "/{vehicleId}", method = RequestMethod.PUT)
    ResponseEntity<VehicleResponseDto> editVehicle(@PathVariable Integer vehicleId, @RequestBody VehicleRequestDto vehicleRequestDto);


    @RequestMapping(value = "/{vehicleId}", method = RequestMethod.DELETE)
    ResponseEntity<VehicleResponseDto> removeVehicle(@PathVariable Integer vehicleId);

    @RequestMapping(value = "", method = RequestMethod.GET)
    ResponseEntity<List<VehicleResponseDto>> getUserVehicles();

}
