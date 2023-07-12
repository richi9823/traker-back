package com.ricardo.traker.model.entity;

import lombok.*;

import jakarta.persistence.*;
import lombok.experimental.SuperBuilder;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Getter
@Setter
@AllArgsConstructor
@Table(name = "position", schema = "public")
@Entity(name = "position")
@SuperBuilder
@NoArgsConstructor
public class PositionEntity extends SuperEntity{

    @Id
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
