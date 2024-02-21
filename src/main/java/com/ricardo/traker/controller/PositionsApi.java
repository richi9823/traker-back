package com.ricardo.traker.controller;

import com.ricardo.traker.model.dto.response.FullPositionResponseDto;
import com.ricardo.traker.model.dto.response.PositionResponseDto;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@RequestMapping("/api/position")
@SecurityRequirement(name = "Bearer Authentication")
public interface PositionsApi {

    @RequestMapping(value = "/vehicle/{vehicleId}", method = RequestMethod.GET)
    FullPositionResponseDto getLastPosition(@PathVariable Long vehicleId);


}
