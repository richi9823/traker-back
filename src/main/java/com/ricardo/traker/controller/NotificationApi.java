package com.ricardo.traker.controller;

import com.ricardo.traker.model.dto.request.AlertRequest.AlertRequestDto;
import com.ricardo.traker.model.dto.response.AlertResponse.AlertResponseDto;
import com.ricardo.traker.model.dto.response.ListResponse;
import com.ricardo.traker.model.dto.response.NotificationResponseDto;
import com.ricardo.traker.model.dto.response.NotificationShortResponseDto;
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
    ResponseEntity<ListResponse<NotificationShortResponseDto>> getNotifications(@RequestParam(required = false) Long vehicleId,
                                                                                @RequestParam(required = false) Long alertId,
                                                                                @RequestParam(required = false, defaultValue = "false") Boolean readed,
                                                                                @RequestParam(value = "page", required = false, defaultValue = "0") Integer page,
                                                                                @RequestParam(value = "size", required = false, defaultValue = "5") Integer size,
                                                                                @RequestParam(value = "sort", required = false, defaultValue = "read") String sort);


}
