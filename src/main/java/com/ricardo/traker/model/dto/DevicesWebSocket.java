package com.ricardo.traker.model.dto;

import lombok.Data;

import java.util.Date;

@Data
public class DevicesWebSocket {

    private Long id;
    private String name;

    private String uniqueId;

    private String status;

    private Date lastUpdate;

    private Boolean disabled;

    private Integer positionId;

    private Integer groupId;

    private String phone;

    private String model;

    private String contact;

    private String category;

}


