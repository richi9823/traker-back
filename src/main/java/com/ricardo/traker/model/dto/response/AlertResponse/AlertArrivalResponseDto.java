package com.ricardo.traker.model.dto.response.AlertResponse;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class AlertArrivalResponseDto {

    private BigDecimal latitude;

    private BigDecimal longitude;
}
