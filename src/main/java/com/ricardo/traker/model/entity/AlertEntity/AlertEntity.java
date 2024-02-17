package com.ricardo.traker.model.entity.AlertEntity;

import com.ricardo.traker.enums.TypeAlertEnum;
import com.ricardo.traker.model.entity.NotificationEntity;
import com.ricardo.traker.model.entity.SuperEntity;
import com.ricardo.traker.model.entity.VehicleEntity;
import com.ricardo.traker.traccar.Notification;
import lombok.Data;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.Where;

import java.util.List;

@SuperBuilder
@Getter
@Setter
@NoArgsConstructor
@Table(name = "alert", schema = "public")
@Entity(name = "alert")
@Inheritance(strategy = InheritanceType.JOINED)
@Where(clause = "deleted_at IS NULL")
@SQLDelete(sql = "UPDATE alert SET deleted_at = now() WHERE id = ?")
public class AlertEntity extends SuperEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;

    private String name;

    @Column(columnDefinition = "boolean default false")
    private boolean silenced;

    @Enumerated(EnumType.STRING)
    private TypeAlertEnum type;

    @OneToMany(mappedBy = "alert")
    private List<NotificationEntity> notifications;

    @JoinTable(
            name = "alert_vehicle",
            joinColumns = @JoinColumn(name = "alert_id", nullable = false),
            inverseJoinColumns = @JoinColumn(name="vehicle_id", nullable = false)
    )
    @ManyToMany
    List<VehicleEntity> vehicles;
}
