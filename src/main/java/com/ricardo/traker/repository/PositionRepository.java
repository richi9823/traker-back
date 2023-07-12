package com.ricardo.traker.repository;

import com.ricardo.traker.model.entity.PositionEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PositionRepository extends JpaRepository<PositionEntity, Integer> {

    List<PositionEntity> findByGps_TraccarDeviceIdOrderByTimeDesc(Integer gpsId);
}
