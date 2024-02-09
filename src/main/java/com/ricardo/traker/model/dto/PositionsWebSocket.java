package com.ricardo.traker.model.dto;


import lombok.Data;


import java.math.BigDecimal;

import java.util.Date;

@Data
public class PositionsWebSocket {

    private Long id;

    private Long deviceId;

    private AttributesWebsocket attributes;

    private String protocol;

    private Date deviceTime;

    private Date fixTime;

    private Date serverTime;

    private Boolean outdated;

    private Boolean valid;

    private BigDecimal latitude;

    private BigDecimal longitude;

    private BigDecimal altitude;

    private BigDecimal speed;

    private Integer course;

    private String address;

    private BigDecimal accuracy;
}
