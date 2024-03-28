package com.ricardo.traker.model.entity.AlertEntity;

import lombok.*;

import jakarta.persistence.*;
import lombok.experimental.SuperBuilder;

import java.math.BigDecimal;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Table(name = "alert_arrival", schema = "public")
@Entity(name = "alert_arrival")
@SuperBuilder
public class AlertArrivalEntity extends  AlertEntity{

    @Column(nullable = false)
    private BigDecimal latitude;

    @Column(nullable = false)
    private BigDecimal longitude;

    @Column(nullable = false)
    private Long radio;
}
