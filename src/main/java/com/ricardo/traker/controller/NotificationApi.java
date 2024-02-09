package com.ricardo.traker.controller;

import com.ricardo.traker.model.dto.request.AlertRequest.AlertRequestDto;
import com.ricardo.traker.model.dto.response.AlertResponse.AlertResponseDto;
import com.ricardo.traker.model.dto.response.ListResponse;
import com.ricardo.traker.model.dto.response.NotificationResponseDto;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@RequestMapping("/api/notification")
@SecurityRequirement(name = "Bearer Authentication")
public interface NotificationApi {

    @RequestMapping(value = "/{notificationId}", method = RequestMethod.PUT)
    ResponseEntity<?> readNotification(@PathVariable Long notificationId);

    @RequestMapping(value = "/{notificationId}", method = RequestMethod.GET)
    ResponseEntity<NotificationResponseDto> getNotification(@PathVariable Long notificationId);

    @RequestMapping(value = "", method = RequestMethod.GET)
    ResponseEntity<ListResponse<NotificationResponseDto>> getNotifications(@RequestParam(value = "page", required = false, defaultValue = "0") Integer page,
                                                                           @RequestParam(value = "size", required = false, defaultValue = "5") Integer size,
                                                                           @RequestParam(value = "sort", required = false, defaultValue = "modifiedDate") String sort);

    @RequestMapping(value = "/pending", method = RequestMethod.GET)
    ResponseEntity<ListResponse<NotificationResponseDto>> getPendingNotifications(@RequestParam(value = "page", required = false, defaultValue = "0") Integer page,
                                                                          @RequestParam(value = "size", required = false, defaultValue = "5") Integer size,
                                                                          @RequestParam(value = "sort", required = false, defaultValue = "modifiedDate") String sort);

    @RequestMapping(value = "/vehicle/{vehicleId}/notifications", method = RequestMethod.GET)
    ResponseEntity<ListResponse<NotificationResponseDto>> getVehicleNotifications(@PathVariable Long vehicleId,
                                                                          @RequestParam(value = "page", required = false, defaultValue = "0") Integer page,
                                                                          @RequestParam(value = "size", required = false, defaultValue = "5") Integer size,
                                                                          @RequestParam(value = "sort", required = false, defaultValue = "modifiedDate") String sort);

    @RequestMapping(value = "/alert/{alertId}/notifications", method = RequestMethod.GET)
    ResponseEntity<ListResponse<NotificationResponseDto>> getAlertNotifications(@PathVariable Long alertId,
                                                                        @RequestParam(value = "page", required = false, defaultValue = "0") Integer page,
                                                                        @RequestParam(value = "size", required = false, defaultValue = "5") Integer size,
                                                                        @RequestParam(value = "sort", required = false, defaultValue = "modifiedDate") String sort);

}
