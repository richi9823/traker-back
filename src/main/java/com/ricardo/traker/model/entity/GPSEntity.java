package com.ricardo.traker.model.entity;

import com.ricardo.traker.enums.GPSStatusEnum;
import lombok.*;
import jakarta.persistence.*;
import lombok.experimental.SuperBuilder;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.Where;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.OffsetDateTime;

@Getter
@Setter
@AllArgsConstructor
@Table(name = "gps_device", schema = "public")
@Entity(name = "gps_device")
@SuperBuilder
@NoArgsConstructor
@Where(clause = "deleted_at IS NULL")
@SQLDelete(sql = "UPDATE gps_device SET deleted_at = now() WHERE traccar_device_id = ?")
public class GPSEntity extends SuperEntity{

    @Id
    @Column(name="traccar_device_id")
    private Long traccarDeviceId;

    @Column(name="register_device_id", unique = true, nullable = false)
    private Long registerDeviceId;

    @Column(name = "last_updated")
    private OffsetDateTime lastUpdated;

    private String name;

    @Enumerated(EnumType.STRING)
    private GPSStatusEnum status;

    @Column(name = "traccar_status")
    private String traccarStatus;

    @Column(name = "total_distance")
    private BigDecimal totalDistance;

    @Column(name = "actual_distance")
    private BigDecimal actualDistance;

    private Boolean motion;

    @ManyToOne
    @JoinColumn(name = "vehicle_id")
    private VehicleEntity vehicle;


}
