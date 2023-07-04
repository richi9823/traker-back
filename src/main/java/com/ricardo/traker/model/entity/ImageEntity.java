package com.ricardo.traker.model.entity;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.GenericGenerator;

import jakarta.persistence.*;

@Table(name = "image", schema = "public")
@Entity(name = "image")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ImageEntity {

    @Id
    @GeneratedValue(generator="system-uuid", strategy = GenerationType.AUTO)
    @GenericGenerator(name="system-uuid", strategy = "uuid")
    private String id;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private String type;

    @Lob
    @Column(name = "image_data", length = 1000, nullable = false)
    private byte[] imageData;

    @ManyToOne
    @JoinColumn(name = "vehicle_id")
    private VehicleEntity vehicleEntity;
}
