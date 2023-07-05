package com.ricardo.traker.model.dto.request.AlertRequest;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.math.BigDecimal;

@Data
public class AlertSpeedRequestDto {

    @JsonProperty("speed_limit")
    private BigDecimal speedLimit;
}
