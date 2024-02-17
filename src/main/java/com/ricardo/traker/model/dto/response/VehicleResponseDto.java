package com.ricardo.traker.model.dto.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.ricardo.traker.model.dto.response.AlertResponse.AlertShortResponseDto;
import lombok.Builder;
import lombok.Data;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.OffsetDateTime;
import java.util.ArrayList;
import java.util.List;

@Data
@Builder
public class VehicleResponseDto {

    private Long id;

    private String model;

    private String license;

    private String image;

    @Builder.Default
    private List<GPSShortResponseDto> gps = new ArrayList<>();

    @JsonProperty("created_date")
    private OffsetDateTime createdDate;

    @JsonProperty("modified_date")
    private OffsetDateTime modifiedDate;

    @JsonProperty("total_distance")
    private BigDecimal totalDistance;


}
