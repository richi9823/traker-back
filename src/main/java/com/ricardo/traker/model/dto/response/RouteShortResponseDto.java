package com.ricardo.traker.model.dto.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.math.BigDecimal;
import java.time.OffsetDateTime;

@Data
public class RouteShortResponseDto {

    OffsetDateTime init;

    OffsetDateTime finish;

    @JsonProperty("total_distance")
    private BigDecimal totalDistance;

}
