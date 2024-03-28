package com.ricardo.traker.model.dto.request.AlertRequest;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.math.BigDecimal;

@Data
public class AlertDistanceRouteRequestDto {

    @JsonProperty("max_distance")
    private BigDecimal maxDistance;
}
