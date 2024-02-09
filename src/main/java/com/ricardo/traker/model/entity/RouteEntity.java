package com.ricardo.traker.model.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.Where;

import java.time.LocalDateTime;
import java.time.OffsetDateTime;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@Table(name = "route", schema = "public")
@Entity(name = "route")
@SuperBuilder
@NoArgsConstructor
@Where(clause = "deleted_at IS NULL")
@SQLDelete(sql = "UPDATE route SET deleted_at = now() WHERE id = ?")
public class RouteEntity extends SuperEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(nullable = false, updatable = false)
    private OffsetDateTime start;

    private OffsetDateTime finish;

    @OneToMany(mappedBy = "route")
    private List<PositionEntity> positions;

    @ManyToOne
    private GPSEntity gps;
}
