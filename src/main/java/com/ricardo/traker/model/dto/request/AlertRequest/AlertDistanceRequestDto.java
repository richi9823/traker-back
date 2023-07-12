package com.ricardo.traker.model.dto.request.AlertRequest;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.math.BigDecimal;

@Data
public class AlertDistanceRequestDto {

    @JsonProperty("max_distance")
    private BigDecimal maxDistance;

    @JsonProperty("point_reference_latitude")
    private BigDecimal pointReferenceLatitude;

    @JsonProperty("point_reference_longitude")
    private BigDecimal pointReferenceLongitude;
}
