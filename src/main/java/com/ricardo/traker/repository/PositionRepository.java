package com.ricardo.traker.repository;

import com.ricardo.traker.model.entity.PositionEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PositionRepository extends JpaRepository<PositionEntity, Integer> {
}
