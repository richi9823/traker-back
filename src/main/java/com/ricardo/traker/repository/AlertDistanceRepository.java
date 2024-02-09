package com.ricardo.traker.repository;

import com.ricardo.traker.model.entity.AlertEntity.AlertDistanceEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AlertDistanceRepository extends JpaRepository<AlertDistanceEntity, Long> {
}
