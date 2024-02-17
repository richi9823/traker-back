package com.ricardo.traker.model.dto.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.ricardo.traker.model.dto.response.AlertResponse.AlertShortResponseDto;
import lombok.Data;

import java.time.OffsetDateTime;

@Data
public class NotificationShortResponseDto {

    private Long id;

    private boolean read;

    private AlertShortResponseDto alert;

    private VehicleShortResponseDto vehicle;

    @JsonProperty("created_date")
    private OffsetDateTime createdDate;
}
