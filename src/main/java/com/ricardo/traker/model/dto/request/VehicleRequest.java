package com.ricardo.traker.model.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import org.springframework.validation.annotation.Validated;


@Data
@Validated
public class VehicleRequest {

    @NotNull
    @NotBlank
    private String model;

    @NotNull
    @NotBlank
    private String license;

    @NotNull
    private Integer deviceRegisterId;

}
