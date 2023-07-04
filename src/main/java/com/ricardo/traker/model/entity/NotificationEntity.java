package com.ricardo.traker.model.entity;

import com.ricardo.traker.model.entity.AlertEntity.AlertEntity;
import lombok.Data;

import jakarta.persistence.*;
import java.util.List;

@Data
@Table(name = "notification", schema = "public")
@Entity(name = "notification")
public class NotificationEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;

    @Column(nullable = false)
    private boolean read;

    @ManyToOne
    @JoinColumn(name = "alert_id", nullable = false, updatable = false)
    private AlertEntity alert;

    @OneToMany
    private List<PositionEntity> positions;
}
