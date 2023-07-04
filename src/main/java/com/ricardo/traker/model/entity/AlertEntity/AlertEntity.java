package com.ricardo.traker.model.entity.AlertEntity;

import com.ricardo.traker.enums.TypeAlertEnum;
import com.ricardo.traker.model.entity.VehicleEntity;
import lombok.Data;
import jakarta.persistence.*;

@Data
@Table(name = "alert", schema = "public")
@Entity(name = "alert")
@Inheritance(strategy = InheritanceType.JOINED)
public class AlertEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;

    private String name;

    private boolean silenced;

    @Enumerated(EnumType.STRING)
    private TypeAlertEnum type;

    @ManyToOne
    @JoinColumn(name = "vehicle_id", nullable = false, updatable = false)
    private VehicleEntity vehicleEntity;


}
