package com.ricardo.traker.model.entity;

import lombok.*;
import lombok.experimental.SuperBuilder;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.Where;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import jakarta.persistence.*;
import java.time.LocalDateTime;
import java.time.OffsetDateTime;

@SuperBuilder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@MappedSuperclass
public class SuperEntity {

    @Column(name = "created_date", nullable = false, updatable = false)
    private OffsetDateTime createdDate;

    @Column(name = "modified_date")
    private OffsetDateTime modifiedDate;

    @Column(name = "deleted_at")
    private OffsetDateTime deletedAt;

    @PrePersist
    private void prePersist(){
        this.createdDate = OffsetDateTime.now();
        this.modifiedDate = OffsetDateTime.now();
    }

    @PreUpdate
    private void preUpdate(){
        this.modifiedDate = OffsetDateTime.now();
    }

}
