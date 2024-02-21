package com.ricardo.traker.repository;


import com.ricardo.traker.model.entity.PositionEntity;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

public interface PositionRepository extends JpaRepository<PositionEntity, Long> {

    void deleteByRoute_id(long routeId);

    List<PositionEntity> findOneByRoute_Gps_Vehicle_IdOrderByTimeDesc(Long vehicleId);
}
