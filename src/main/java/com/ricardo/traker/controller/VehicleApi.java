package com.ricardo.traker.controller;

import com.ricardo.traker.exception.ServiceException;
import com.ricardo.traker.model.dto.request.VehicleRequestDto;

import com.ricardo.traker.model.dto.response.ListResponse;
import com.ricardo.traker.model.dto.response.VehicleResponseDto;

import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequestMapping("/api/vehicle")
@SecurityRequirement(name = "Bearer Authentication")
public interface VehicleApi {


    @RequestMapping(value = "", method = RequestMethod.POST)
    ResponseEntity<VehicleResponseDto> registerVehicle(@RequestBody @Valid VehicleRequestDto vehicleRequestDto) throws ServiceException;

    @RequestMapping(value = "/{vehicleId}", method = RequestMethod.GET)
    ResponseEntity<VehicleResponseDto> getVehicle(@PathVariable Long vehicleId);

    @RequestMapping(value = "/{vehicleId}", method = RequestMethod.PUT)
    ResponseEntity<VehicleResponseDto> editVehicle(@PathVariable Long vehicleId, @RequestBody VehicleRequestDto vehicleRequestDto);


    @RequestMapping(value = "/{vehicleId}", method = RequestMethod.DELETE)
    ResponseEntity<VehicleResponseDto> removeVehicle(@PathVariable Long vehicleId);

    @RequestMapping(value = "", method = RequestMethod.GET)
    ResponseEntity<ListResponse<VehicleResponseDto>> getUserVehicles(@RequestParam(value = "page", required = false, defaultValue = "0") Integer page,
                                                                     @RequestParam(value = "size", required = false, defaultValue = "5") Integer size,
                                                                     @RequestParam(value = "sort", required = false, defaultValue = "modifiedDate")String sort);

}
