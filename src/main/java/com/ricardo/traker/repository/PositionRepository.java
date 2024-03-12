package com.ricardo.traker.repository;


import com.ricardo.traker.model.entity.PositionEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PositionRepository extends JpaRepository<PositionEntity, Long> {

    void deleteByRoute_Id(long routeId);

    List<PositionEntity> findOneByRoute_Gps_Vehicle_IdOrderByTimeDesc(Long vehicleId);
}
