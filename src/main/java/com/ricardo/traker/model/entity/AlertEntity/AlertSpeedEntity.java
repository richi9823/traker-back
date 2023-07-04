package com.ricardo.traker.model.entity.AlertEntity;

import lombok.Data;
import jakarta.persistence.*;
import java.math.BigDecimal;

@Data
@Table(name = "alert_speed", schema = "public")
@Entity(name = "alert_speed")
public class AlertSpeedEntity extends  AlertEntity{

    @Column(name = "speed_limit", nullable = false)
    private BigDecimal speedLimit;
}
