package com.ricardo.traker.model.dto.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;


@Data
public class VehicleRequestDto {

    @NotNull
    @NotBlank
    private String model;

    @NotNull
    @NotBlank
    private String license;

    @NotNull
    @JsonProperty("device_register_id")
    private Long deviceRegisterId;

}
