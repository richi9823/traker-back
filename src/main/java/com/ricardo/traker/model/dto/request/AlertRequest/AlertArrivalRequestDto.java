package com.ricardo.traker.model.dto.request.AlertRequest;

import jakarta.persistence.Column;
import lombok.Data;

import java.math.BigDecimal;

@Data
public class AlertArrivalRequestDto {

    private Long radio;

    private BigDecimal latitude;

    private BigDecimal longitude;
}
