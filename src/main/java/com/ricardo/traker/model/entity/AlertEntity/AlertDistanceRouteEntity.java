package com.ricardo.traker.model.entity.AlertEntity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

import java.math.BigDecimal;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Table(name = "alert_distance_route", schema = "public")
@Entity(name = "alert_distance_route")
@SuperBuilder
public class AlertDistanceRouteEntity extends AlertEntity{

    @Column(name="max_distance" ,nullable = false)
    private BigDecimal maxDistance;
}
