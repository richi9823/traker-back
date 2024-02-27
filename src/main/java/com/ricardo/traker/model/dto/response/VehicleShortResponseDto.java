package com.ricardo.traker.model.dto.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.time.OffsetDateTime;

@Data
public class VehicleShortResponseDto {

    private Long id;

    private String model;

    private String license;

    @JsonProperty("modified_date")
    private OffsetDateTime modifiedDate;

}
