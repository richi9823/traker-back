package com.ricardo.traker.model.dto.response.AlertResponse;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AlertDistanceResponseDto {

    @JsonProperty("max_distance")
    private BigDecimal maxDistance;
}
