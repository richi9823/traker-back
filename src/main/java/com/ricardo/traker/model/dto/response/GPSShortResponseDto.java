package com.ricardo.traker.model.dto.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.ricardo.traker.enums.GPSStatusEnum;
import lombok.Data;

@Data
public class GPSShortResponseDto {

    private Long id;

    @JsonProperty("register_device_id")
    private Long registerDeviceId;

    private String name;

    private GPSStatusEnum status;
}
