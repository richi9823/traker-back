package com.ricardo.traker.model.dto.response.AlertResponse;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.ricardo.traker.enums.TypeAlertEnum;
import lombok.Data;

@Data
public class AlertResponseDto {

    private Integer id;

    private String name;

    private Boolean silenced;

    private TypeAlertEnum type;

    @JsonProperty("vehicle_id")
    private Integer vehicleId;

    private AlertArrivalResponseDto arrival;

    private AlertDistanceResponseDto distance;

    private AlertSpeedResponseDto speed;
}
