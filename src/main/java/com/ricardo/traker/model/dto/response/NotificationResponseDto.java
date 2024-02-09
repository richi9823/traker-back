package com.ricardo.traker.model.dto.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.ricardo.traker.model.dto.response.AlertResponse.AlertResponseDto;
import lombok.Data;

import java.time.LocalDateTime;
import java.time.OffsetDateTime;
import java.util.List;

@Data
public class NotificationResponseDto {

    private Long id;

    private boolean read;

    private AlertResponseDto alert;

    private List<PositionsResponseDto> positions;

    @JsonProperty("created_date")
    private OffsetDateTime createdDate;
}
