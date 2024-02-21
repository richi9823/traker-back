package com.ricardo.traker.model.dto.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.math.BigDecimal;
import java.time.OffsetDateTime;

@Data
public class FullPositionResponseDto {

    private Long id;

    private OffsetDateTime time;

    private BigDecimal latitude;

    private BigDecimal longitude;

    private BigDecimal altitude;

    private BigDecimal speed;

    @JsonProperty("created_date")
    private OffsetDateTime createdDate;

    private GPSResponseDto gps;
}
