package com.ricardo.traker.model.dto.request.AlertRequest;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.ricardo.traker.enums.TypeAlertEnum;
import jakarta.validation.constraints.NotNull;
import lombok.Data;


@Data
public class AlertRequestDto {

    private String name;

    @NotNull
    private Boolean silenced;

    @NotNull
    private TypeAlertEnum type;

    @JsonProperty("vehicle_id")
    @NotNull
    private Integer vehicleId;

    private AlertArrivalRequestDto arrival;

    private AlertDistanceRequestDto distance;

    private AlertSpeedRequestDto speed;

}
