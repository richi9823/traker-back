package com.ricardo.traker.model.entity.AlertEntity;

import lombok.*;
import jakarta.persistence.*;
import lombok.experimental.SuperBuilder;

import java.math.BigDecimal;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Table(name = "alert_speed", schema = "public")
@Entity(name = "alert_speed")
@SuperBuilder
public class AlertSpeedEntity extends  AlertEntity{

    @Column(name = "speed_limit", nullable = false)
    private BigDecimal speedLimit;
}
