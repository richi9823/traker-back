package com.ricardo.traker.repository;

import com.ricardo.traker.model.entity.AlertEntity.AlertSpeedEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AlertSpeedRepository extends JpaRepository<AlertSpeedEntity, Long> {
}
