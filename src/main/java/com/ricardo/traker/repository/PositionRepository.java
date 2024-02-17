package com.ricardo.traker.repository;

import com.ricardo.traker.enums.IntervalEnum;
import com.ricardo.traker.model.entity.PositionEntity;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.time.LocalDateTime;
import java.util.List;

public interface PositionRepository extends JpaRepository<PositionEntity, Long>, JpaSpecificationExecutor<PositionEntity> {

    void deleteByRoute_id(long routeId);
}
