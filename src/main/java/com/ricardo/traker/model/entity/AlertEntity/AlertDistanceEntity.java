package com.ricardo.traker.model.entity.AlertEntity;

import lombok.Data;

import jakarta.persistence.*;
import java.math.BigDecimal;

@Data
@Table(name = "alert_distance", schema = "public")
@Entity(name = "alert_distance")
public class AlertDistanceEntity extends  AlertEntity{

    @Column(name="max_distance" ,nullable = false)
    private BigDecimal maxDistance;
}
