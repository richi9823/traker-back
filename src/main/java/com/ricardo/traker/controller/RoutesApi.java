package com.ricardo.traker.controller;

import com.ricardo.traker.model.dto.response.ListResponse;
import com.ricardo.traker.model.dto.response.RouteResponseDto;
import com.ricardo.traker.model.dto.response.RouteShortResponseDto;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import java.time.LocalDate;
import java.time.LocalDateTime;

@RequestMapping("/api/route")
@SecurityRequirement(name = "Bearer Authentication")
public interface RoutesApi {

    @RequestMapping(value = "/vehicle/{vehicleId}", method = RequestMethod.GET)
    ResponseEntity<ListResponse<RouteShortResponseDto>> getVehicleRoutes(@PathVariable Long vehicleId,
                                                                         @RequestParam(value = "page", required = false, defaultValue = "0") Integer page,
                                                                         @RequestParam(value = "size", required = false, defaultValue = "5") Integer size,
                                                                         @RequestParam(value = "since", required = false) LocalDate since,
                                                                         @RequestParam(value = "interval", required = false) LocalDate interval);

    @RequestMapping(value = "/{routeId}", method = RequestMethod.GET)
    ResponseEntity<RouteResponseDto> getRoute(@PathVariable Long routeId);

    @RequestMapping(value = "/{routeId}", method = RequestMethod.DELETE)
    ResponseEntity<Void> deleteRoute(@PathVariable Long routeId);
}
