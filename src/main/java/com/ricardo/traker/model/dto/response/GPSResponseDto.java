package com.ricardo.traker.model.dto.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
public class GPSResponseDto {

    @JsonProperty("traccar_device_id")
    private Integer traccarDeviceId;

    @JsonProperty("register_device_id")
    private Integer registerDeviceId;

    @JsonProperty("last_updated")
    private LocalDateTime lastUpdated;

    private String status;

    @JsonProperty("total_distance")
    private BigDecimal totalDistance;

    @JsonProperty("actual_distance")
    private BigDecimal actualDistance;
}
