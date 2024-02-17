package com.ricardo.traker.model.dto.response.AlertResponse;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.ricardo.traker.enums.TypeAlertEnum;
import com.ricardo.traker.model.dto.response.NotificationShortResponseDto;
import com.ricardo.traker.model.dto.response.VehicleShortResponseDto;
import lombok.Data;

import java.time.LocalDateTime;
import java.time.OffsetDateTime;
import java.util.Set;

@Data
public class AlertResponseDto {

    private Long id;

    private String name;

    private Boolean silenced;

    private TypeAlertEnum type;

    private Set<VehicleShortResponseDto> vehicles;

    private AlertArrivalResponseDto arrival;

    private AlertDistanceResponseDto distance;

    private AlertSpeedResponseDto speed;

    @JsonProperty("created_date")
    private OffsetDateTime createdDate;

    @JsonProperty("modified_date")
    private OffsetDateTime modifiedDate;
}
