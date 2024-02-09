package com.ricardo.traker.model.entity;

import lombok.*;

import jakarta.persistence.*;
import lombok.experimental.SuperBuilder;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.Where;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.OffsetDateTime;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@Table(name = "position", schema = "public")
@Entity(name = "position")
@SuperBuilder
@NoArgsConstructor
@Where(clause = "deleted_at IS NULL")
@SQLDelete(sql = "UPDATE position SET deleted_at = now() WHERE id = ?")
public class PositionEntity extends SuperEntity{

    @Id
    private Long id;

    private OffsetDateTime time;

    private BigDecimal latitude;

    private BigDecimal longitude;

    private BigDecimal altitude;

    private BigDecimal speed;

    @ManyToOne
    @JoinColumn(name = "route_id", nullable = false, updatable = false)
    private RouteEntity route;

    @ManyToMany(mappedBy = "positions")
    private List<NotificationEntity> notifications;

}
