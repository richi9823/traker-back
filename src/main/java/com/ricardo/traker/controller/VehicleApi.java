package com.ricardo.traker.controller;

import com.ricardo.traker.exception.ServiceException;
import com.ricardo.traker.model.dto.request.VehicleRequest;

import com.ricardo.traker.model.dto.response.VehicleResponseDto;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequestMapping("/api/vehicle")
public interface VehicleApi {


    @RequestMapping(value = "", method = RequestMethod.POST)
    ResponseEntity<VehicleResponseDto> registerVehicle(@RequestBody @Valid VehicleRequest vehicleRequest) throws ServiceException;


    @RequestMapping(value = "/{vehicleId}", method = RequestMethod.PUT)
    ResponseEntity<VehicleResponseDto> editVehicle(@PathVariable Integer vehicleId, @RequestBody VehicleRequest vehicleRequest);


    @RequestMapping(value = "/{vehicleId}", method = RequestMethod.DELETE)
    ResponseEntity<VehicleResponseDto> removeVehicle(@PathVariable Integer vehicleId);

    @RequestMapping(value = "", method = RequestMethod.GET)
    ResponseEntity<List<VehicleResponseDto>> getUserVehicles();

}
