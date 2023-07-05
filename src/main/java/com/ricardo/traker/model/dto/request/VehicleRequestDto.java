package com.ricardo.traker.model.dto.request;

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
    private Integer deviceRegisterId;

}
