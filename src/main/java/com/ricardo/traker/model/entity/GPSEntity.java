package com.ricardo.traker.model.entity;

import lombok.*;
import jakarta.persistence.*;
import lombok.experimental.SuperBuilder;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Getter
@Setter
@AllArgsConstructor
@Table(name = "gps_device", schema = "public")
@Entity(name = "gps_device")
@SuperBuilder
@NoArgsConstructor
public class GPSEntity extends SuperEntity{

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

    @OneToOne(cascade = CascadeType.REMOVE, orphanRemoval = true)
    @JoinColumn(name = "vehicle_id")
    private VehicleEntity vehicle;


}
