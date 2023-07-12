package com.ricardo.traker.controller;

import com.ricardo.traker.enums.IntervalEnum;
import com.ricardo.traker.model.dto.response.NotificationResponseDto;
import com.ricardo.traker.model.dto.response.PositionsResponseDto;
import org.joda.time.Interval;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import java.time.LocalDateTime;
import java.util.List;

@RequestMapping("/api/position")
public interface PositionsApi {

    @RequestMapping(value = "/vehicle/{vehicleId}/positions", method = RequestMethod.GET)
    ResponseEntity<List<PositionsResponseDto>> getVehiclePositions(@PathVariable Integer vehicleId,
                                                                   @RequestParam(value = "since", required = false) String since,
                                                                   @RequestParam(value = "interval", required = false) IntervalEnum interval);
}
