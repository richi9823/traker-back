package com.ricardo.traker.model.dto.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import java.math.BigDecimal;

@Data
public class VehicleResponseDto {

    private Integer id;
    private String model;

    private String license;

    private GPSResponseDto gps;

    @JsonProperty("total_distance")
    private BigDecimal totalDistance;

    @JsonProperty("actual_distance")
    private BigDecimal actualDistance;
}
