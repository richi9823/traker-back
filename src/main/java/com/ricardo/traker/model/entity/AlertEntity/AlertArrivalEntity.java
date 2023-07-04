package com.ricardo.traker.model.entity.AlertEntity;

import lombok.Data;

import jakarta.persistence.*;
import java.math.BigDecimal;

@Data
@Table(name = "alert_arrival", schema = "public")
@Entity(name = "alert_arrival")
public class AlertArrivalEntity extends  AlertEntity{

    @Column(nullable = false)
    private BigDecimal latitude;

    @Column(nullable = false)
    private BigDecimal longitude;
}
