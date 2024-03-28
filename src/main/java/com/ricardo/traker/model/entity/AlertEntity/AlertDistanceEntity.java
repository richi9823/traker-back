package com.ricardo.traker.model.entity.AlertEntity;

import lombok.*;

import jakarta.persistence.*;
import lombok.experimental.SuperBuilder;

import java.math.BigDecimal;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Table(name = "alert_distance", schema = "public")
@Entity(name = "alert_distance")
@SuperBuilder
public class AlertDistanceEntity extends  AlertEntity{

    @Column(name="max_distance" ,nullable = false)
    private BigDecimal maxDistance;

    @Column(nullable = false)
    private BigDecimal pointReferenceLatitude;

    @Column(nullable = false)
    private BigDecimal pointReferenceLongitude;
}
