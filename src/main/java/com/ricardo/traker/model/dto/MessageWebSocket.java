package com.ricardo.traker.model.dto;

import lombok.Data;

import java.util.List;

@Data
public class MessageWebSocket {

    private List<PositionsWebSocket> positions;

    private List<DevicesWebSocket> devices;
}
