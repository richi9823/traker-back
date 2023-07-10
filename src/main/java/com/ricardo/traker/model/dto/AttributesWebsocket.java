package com.ricardo.traker.model.dto;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class AttributesWebsocket {

    private BigDecimal batteryLevel;

    private BigDecimal distance;

    private BigDecimal totalDistance;

    private Boolean motion;
}
