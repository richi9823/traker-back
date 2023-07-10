package com.ricardo.traker.model.entity;

import lombok.Data;
import jakarta.persistence.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
@Table(name = "gps_device", schema = "public")
@Entity(name = "gps_device")
public class GPSEntity {

    @Id
    @Column(name="traccar_device_id")
    private Integer traccarDeviceId;

    @Column(name="register_device_id", unique = true, nullable = false)
    private Integer registerDeviceId;

    @Column(name = "last_updated")
    private LocalDateTime lastUpdated;

    private String status;

    @Column(name = "total_distance")
    private BigDecimal totalDistance;

    @Column(name = "actual_distance")
    private BigDecimal actualDistance;

    private Boolean motion;


}
