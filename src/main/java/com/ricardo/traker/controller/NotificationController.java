package com.ricardo.traker.controller;

import com.ricardo.traker.model.dto.response.AlertResponse.AlertResponseDto;
import com.ricardo.traker.model.dto.response.NotificationResponseDto;
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
    public ResponseEntity<?> readNotification(Integer notificationId) {
        notificationService.readNotification(notificationId);
        return ResponseEntity.ok().build();
    }

    @Override
    public ResponseEntity<List<NotificationResponseDto>> getNotifications() {
        UserDetailsImpl userDetails = tokenUtils.getUser(request);
        if(userDetails == null) return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        return ResponseEntity.ok(
                notificationService.getNotifications(userDetails.getId())
        );
    }

    @Override
    public ResponseEntity<List<NotificationResponseDto>> getPendingNotifications() {
        UserDetailsImpl userDetails = tokenUtils.getUser(request);
        if(userDetails == null) return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        return ResponseEntity.ok(
                notificationService.getPendingNotifications(userDetails.getId())
        );
    }

    @Override
    public ResponseEntity<List<NotificationResponseDto>> getVehicleNotifications(Integer vehicleId) {
        UserDetailsImpl userDetails = tokenUtils.getUser(request);
        if(userDetails == null) return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        return ResponseEntity.ok(
                notificationService.getVehicleNotifications(vehicleId)
        );
    }

    @Override
    public ResponseEntity<List<NotificationResponseDto>> getAlertNotifications(Integer alertId) {
        UserDetailsImpl userDetails = tokenUtils.getUser(request);
        if(userDetails == null) return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        return ResponseEntity.ok(
                notificationService.getAlertNotifications(alertId)
        );
    }
}
