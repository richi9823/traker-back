package com.ricardo.traker.controller;

import com.ricardo.traker.model.dto.request.AlertRequest.AlertRequestDto;
import com.ricardo.traker.model.dto.response.AlertResponse.AlertResponseDto;
import com.ricardo.traker.model.dto.response.ListResponse;
import io.swagger.annotations.ApiParam;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequestMapping("/api/alert")
@SecurityRequirement(name = "Bearer Authentication")
public interface AlertApi {
    @RequestMapping(value = "", method = RequestMethod.POST)
    ResponseEntity<AlertResponseDto> createAlert(@RequestBody @Valid AlertRequestDto alertRequestDto);


    @RequestMapping(value = "/{alertId}", method = RequestMethod.PUT)
    ResponseEntity<AlertResponseDto> editAlert(@PathVariable Long alertId, @RequestBody AlertRequestDto vehicleRequestDto);

    @RequestMapping(value = "/{alertId}", method = RequestMethod.GET)
    ResponseEntity<AlertResponseDto> getAlert(@PathVariable Long alertId);

    @RequestMapping(value = "/{alertId}", method = RequestMethod.DELETE)
    ResponseEntity<?> removeAlert(@PathVariable Long alertId);

    @RequestMapping(value = "/vehicle/{vehicleId}/alerts", method = RequestMethod.GET)
    ResponseEntity<ListResponse<AlertResponseDto>> getVehicleAlerts(@PathVariable Long vehicleId,
                                                                    @RequestParam(value = "page", required = false, defaultValue = "0") Integer page,
                                                                    @RequestParam(value = "size", required = false, defaultValue = "5") Integer size,
                                                                    @RequestParam(value = "sort", required = false, defaultValue = "modifiedDate") String sort);
}
