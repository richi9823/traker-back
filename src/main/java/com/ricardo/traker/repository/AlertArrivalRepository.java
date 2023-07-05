package com.ricardo.traker.repository;

import com.ricardo.traker.model.entity.AlertEntity.AlertArrivalEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AlertArrivalRepository extends JpaRepository<AlertArrivalEntity, Integer> {
}
