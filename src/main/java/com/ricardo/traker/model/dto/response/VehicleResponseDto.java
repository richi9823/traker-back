package com.ricardo.traker.model.dto.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.OffsetDateTime;
import java.util.List;

@Data
public class VehicleResponseDto {

    private Integer id;
    private String model;

    private String license;

    private List<GPSResponseDto> gps;

    @JsonProperty("created_date")
    private OffsetDateTime createdDate;

    @JsonProperty("modified_date")
    private OffsetDateTime modifiedDate;


}
