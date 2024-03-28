package com.ricardo.traker.model.dto.response.AlertResponse;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.math.BigDecimal;

@Data
public class AlertDistanceRouteResponseDto {

    @JsonProperty("max_distance")
    private BigDecimal maxDistance;
}
