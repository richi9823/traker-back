package com.ricardo.traker.model.entity;

import com.ricardo.traker.model.entity.AlertEntity.AlertEntity;
import lombok.*;

import jakarta.persistence.*;
import lombok.experimental.SuperBuilder;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@Table(name = "notification", schema = "public")
@Entity(name = "notification")
@SuperBuilder
@NoArgsConstructor
public class NotificationEntity extends SuperEntity{

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
