package com.ricardo.traker.controller;

import com.ricardo.traker.model.dto.request.AlertRequest.AlertRequestDto;
import com.ricardo.traker.model.dto.response.AlertResponse.AlertResponseDto;
import com.ricardo.traker.model.dto.response.NotificationResponseDto;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.util.List;

@RequestMapping("/api/notification")
public interface NotificationApi {

    @RequestMapping(value = "/{notificationId}", method = RequestMethod.PUT)
    ResponseEntity<?> readNotification(@PathVariable Integer notificationId);

    @RequestMapping(value = "", method = RequestMethod.GET)
    ResponseEntity<List<NotificationResponseDto>> getNotifications();

    @RequestMapping(value = "/pending", method = RequestMethod.GET)
    ResponseEntity<List<NotificationResponseDto>> getPendingNotifications();

    @RequestMapping(value = "/vehicle/{vehicleId}/notifications", method = RequestMethod.GET)
    ResponseEntity<List<NotificationResponseDto>> getVehicleNotifications(@PathVariable Integer vehicleId);

    @RequestMapping(value = "/alert/{alertId}/notifications", method = RequestMethod.GET)
    ResponseEntity<List<NotificationResponseDto>> getAlertNotifications(@PathVariable Integer alertId);

}
