package com.ricardo.traker.model.entity.AlertEntity;

import com.ricardo.traker.enums.TypeAlertEnum;
import com.ricardo.traker.model.entity.NotificationEntity;
import com.ricardo.traker.model.entity.VehicleEntity;
import com.ricardo.traker.traccar.Notification;
import lombok.Data;
import jakarta.persistence.*;

import java.util.List;

@Data
@Table(name = "alert", schema = "public")
@Entity(name = "alert")
@Inheritance(strategy = InheritanceType.JOINED)
public class AlertEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;

    private String name;

    @Column(columnDefinition = "boolean default false")
    private boolean silenced;

    @Enumerated(EnumType.STRING)
    private TypeAlertEnum type;

    @ManyToOne
    @JoinColumn(name = "vehicle_id", nullable = false, updatable = false)
    private VehicleEntity vehicle;

    @OneToMany(mappedBy = "alert")
    private List<NotificationEntity> notifications;


}
