package com.ricardo.traker.model.entity;

import com.ricardo.traker.model.entity.AlertEntity.AlertEntity;
import lombok.*;

import jakarta.persistence.*;
import lombok.experimental.SuperBuilder;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.Where;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@Table(name = "notification", schema = "public")
@Entity(name = "notification")
@SuperBuilder
@NoArgsConstructor
@Where(clause = "deleted_at IS NULL")
@SQLDelete(sql = "UPDATE notification SET deleted_at = now() WHERE id = ?")
public class NotificationEntity extends SuperEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(nullable = false)
    private boolean read;

    @ManyToOne
    @JoinColumn(name = "alert_id", nullable = false, updatable = false)
    private AlertEntity alert;


    @JoinTable(
            name = "notifications_positions",
            joinColumns = @JoinColumn(name = "notification_id", nullable = false),
            inverseJoinColumns = @JoinColumn(name="position_id", nullable = false)
    )
    @ManyToMany
    private List<PositionEntity> positions;
}
