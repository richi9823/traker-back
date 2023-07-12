package com.ricardo.traker.model.dto.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
public class PositionsResponseDto {

    private Integer id;

    private LocalDateTime time;

    private BigDecimal latitude;

    private BigDecimal longitude;

    private BigDecimal altitude;

    private BigDecimal speed;

    @JsonProperty("created_date")
    private LocalDateTime createdDate;
}
