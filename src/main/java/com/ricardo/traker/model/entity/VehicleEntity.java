package com.ricardo.traker.model.entity;

import com.ricardo.traker.model.entity.AlertEntity.AlertEntity;
import lombok.*;

import jakarta.persistence.*;
import lombok.experimental.SuperBuilder;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.Where;

import java.math.BigDecimal;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@Table(name = "vehicle", schema = "public")
@Entity(name = "vehicle")
@SuperBuilder
@NoArgsConstructor
@Where(clause = "deleted_at IS NULL")
@SQLDelete(sql = "UPDATE vehicle SET deleted_at = now() WHERE id = ?")
public class VehicleEntity extends SuperEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;

    @Column(nullable = false)
    private String model;

    @Column(unique = true, nullable = false)
    private String license;

    @OneToOne(cascade = CascadeType.REMOVE, orphanRemoval = true)
    @JoinColumn(name = "image_id")
    private ImageEntity image;

    @OneToMany(mappedBy = "vehicle")
    private List<GPSEntity> gps;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false, updatable = false)
    private UserEntity user;

    @ManyToMany(mappedBy = "vehicles")
    List<AlertEntity> alerts;
}
