package com.ricardo.traker.controller;

import com.ricardo.traker.model.dto.response.AlertResponse.AlertResponseDto;
import com.ricardo.traker.model.dto.response.ListResponse;
import com.ricardo.traker.model.dto.response.NotificationResponseDto;
import com.ricardo.traker.model.dto.response.NotificationShortResponseDto;
import com.ricardo.traker.security.TokenUtils;
import com.ricardo.traker.security.UserDetailsImpl;
import com.ricardo.traker.service.NotificationService;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class NotificationController implements NotificationApi{

    @Autowired
    NotificationService notificationService;

    @Autowired
    TokenUtils tokenUtils;

    private final HttpServletRequest request;

    @Autowired
    public NotificationController(HttpServletRequest request){
        this.request = request;
    }

    @Override
    public ResponseEntity<?> readNotification(Long notificationId) {
        notificationService.readNotification(notificationId);
        return ResponseEntity.ok().build();
    }

    @Override
    public ResponseEntity<NotificationResponseDto> getNotification(Long notificationId) {
        UserDetailsImpl userDetails = tokenUtils.getUser(request);
        if(userDetails == null) return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        return ResponseEntity.ok(
                notificationService.getNotification(notificationId)
        );
    }

    @Override
    public ResponseEntity<ListResponse<NotificationShortResponseDto>> getNotifications(Long vehicleId, Long alertId, Boolean readed, Integer page, Integer size, String sort) {
        UserDetailsImpl userDetails = tokenUtils.getUser(request);
        if(userDetails == null) return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        return ResponseEntity.ok(
                notificationService.getNotifications(userDetails.getId(), alertId, vehicleId, readed, page, size, sort)
        );
    }

}
