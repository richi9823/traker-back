package com.ricardo.traker.model.entity;

import lombok.Data;

import jakarta.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
@Table(name = "position", schema = "public")
@Entity(name = "position")
public class PositionEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;

    private LocalDateTime time;

    private BigDecimal latitude;

    private BigDecimal longitude;

    private BigDecimal altitude;

    private BigDecimal speed;

    @ManyToOne
    @JoinColumn(name = "gps_id", nullable = false, updatable = false)
    private GPSEntity gps;

    @ManyToOne
    @JoinColumn(name = "notification_id")
    private NotificationEntity notification;

}
