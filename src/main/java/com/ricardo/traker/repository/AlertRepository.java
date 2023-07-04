package com.ricardo.traker.repository;

import com.ricardo.traker.model.entity.AlertEntity.AlertEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AlertRepository extends JpaRepository<AlertEntity, Integer> {
}
