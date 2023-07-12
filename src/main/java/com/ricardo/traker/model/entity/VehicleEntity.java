package com.ricardo.traker.model.entity;

import lombok.*;

import jakarta.persistence.*;
import lombok.experimental.SuperBuilder;

import java.math.BigDecimal;

@Getter
@Setter
@AllArgsConstructor
@Table(name = "vehicle", schema = "public")
@Entity(name = "vehicle")
@SuperBuilder
@NoArgsConstructor
public class VehicleEntity extends SuperEntity{

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
