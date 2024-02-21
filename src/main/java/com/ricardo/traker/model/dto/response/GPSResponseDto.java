package com.ricardo.traker.model.dto.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.ricardo.traker.enums.GPSStatusEnum;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.OffsetDateTime;

@Data
public class GPSResponseDto {

    @JsonProperty("traccar_device_id")
    private Long id;

    @JsonProperty("register_device_id")
    private Long registerDeviceId;

    private String name;

    @JsonProperty("last_updated")
    private OffsetDateTime lastUpdated;

    private GPSStatusEnum status;

    @JsonProperty("traccar_status")
    private String traccarStatus;

    @JsonProperty("total_distance")
    private BigDecimal totalDistance;

    @JsonProperty("actual_distance")
    private BigDecimal actualDistance;
}
