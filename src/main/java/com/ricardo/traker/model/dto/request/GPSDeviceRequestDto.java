package com.ricardo.traker.model.dto.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class GPSDeviceRequestDto {

    @JsonProperty("device_register_id")
    private Long deviceRegisterId;

    private String name;
}
