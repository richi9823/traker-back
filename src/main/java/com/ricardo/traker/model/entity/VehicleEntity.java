package com.ricardo.traker.model.entity;

import lombok.Data;

import jakarta.persistence.*;
import java.math.BigDecimal;

@Data
@Table(name = "vehicle", schema = "public")
@Entity(name = "vehicle")
public class VehicleEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;

    @Column(nullable = false)
    private String model;

    @Column(unique = true, nullable = false)
    private String license;

    @OneToOne(cascade = CascadeType.REMOVE, orphanRemoval = true)
    @JoinColumn(name = "gps_id", nullable = false, updatable = false)
    private GPSEntity gps;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false, updatable = false)
    private UserEntity user;
}
