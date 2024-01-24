package com.ricardo.traker.controller;

import com.ricardo.traker.enums.IntervalEnum;
import com.ricardo.traker.model.dto.response.PositionsResponseDto;
import com.ricardo.traker.model.dto.response.RoutesResponseDto;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@RequestMapping("/api/position")
@SecurityRequirement(name = "Bearer Authentication")
public interface PositionsApi {

    @RequestMapping(value = "/vehicle/{vehicleId}/position", method = RequestMethod.GET)
    ResponseEntity<PositionsResponseDto> getVehiclePosition(@PathVariable Integer vehicleId);

    @RequestMapping(value = "/vehicle/{vehicleId}/routes", method = RequestMethod.GET)
    ResponseEntity<List<RoutesResponseDto>> getVehicleRoutes(@PathVariable Integer vehicleId,
                                                             @RequestParam(value = "since", required = false) String since,
                                                             @RequestParam(value = "interval", required = false) IntervalEnum interval);
}
