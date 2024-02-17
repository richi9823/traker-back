package com.ricardo.traker.repository;

import com.ricardo.traker.model.entity.AlertEntity.AlertEntity;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.util.List;

public interface AlertRepository extends JpaRepository<AlertEntity, Long> {
}
