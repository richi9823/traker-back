package com.ricardo.traker.configuration;

import com.ricardo.traker.model.dto.MessageWebSocket;
import com.ricardo.traker.service.GPSService;
import com.ricardo.traker.service.PositionService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;


@Component
@Slf4j
public class WebsocketHandler {

    private static GPSService gpsService;

    @Autowired
    public WebsocketHandler(PositionService positionService,
                            GPSService gpsService){
        this.gpsService = gpsService;
    }

    public void saveDeviceInfo(MessageWebSocket messageWebSocket) {
        gpsService.updateGPS(messageWebSocket);
    }
}
