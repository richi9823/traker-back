package com.ricardo.traker.model.dto.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import java.math.BigDecimal;
import java.time.OffsetDateTime;
import java.util.List;
@Data
@AllArgsConstructor
@Builder
public class RouteResponseDto {

    private OffsetDateTime init;

    private OffsetDateTime finish;

    @JsonProperty("total_distance")
    private BigDecimal totalDistance;

    private List<PositionResponseDto> positions;

    private GPSShortResponseDto gps;
}
