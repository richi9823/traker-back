package com.ricardo.traker.model.dto.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import org.springframework.web.multipart.MultipartFile;

import java.util.Optional;


@Data
public class VehicleRequestDto {

    @NotNull
    @NotBlank
    private String model;

    @NotNull
    @NotBlank
    private String license;

    private String description;

    private GPSDeviceRequestDto deviceRequestDto;

    public Optional<GPSDeviceRequestDto> getDeviceRequestDto() {
        return Optional.ofNullable(deviceRequestDto);
    }


}
