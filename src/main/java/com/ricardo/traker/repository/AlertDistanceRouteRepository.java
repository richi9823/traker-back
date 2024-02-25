package com.ricardo.traker.repository;

import com.ricardo.traker.model.entity.AlertEntity.AlertDistanceRouteEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AlertDistanceRouteRepository extends JpaRepository<AlertDistanceRouteEntity, Long> {
}
