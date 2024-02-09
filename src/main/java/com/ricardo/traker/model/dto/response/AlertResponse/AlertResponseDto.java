package com.ricardo.traker.model.dto.response.AlertResponse;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.ricardo.traker.enums.TypeAlertEnum;
import lombok.Data;

import java.time.LocalDateTime;
import java.time.OffsetDateTime;

@Data
public class AlertResponseDto {

    private Long id;

    private String name;

    private Boolean silenced;

    private TypeAlertEnum type;

    @JsonProperty("vehicle_id")
    private Long vehicleId;

    private AlertArrivalResponseDto arrival;

    private AlertDistanceResponseDto distance;

    private AlertSpeedResponseDto speed;

    @JsonProperty("created_date")
    private OffsetDateTime createdDate;

    @JsonProperty("modified_date")
    private OffsetDateTime modifiedDate;
}
