package com.ricardo.traker.repository;

import com.ricardo.traker.model.entity.AlertEntity.AlertEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface AlertRepository extends JpaRepository<AlertEntity, Integer> {

    List<AlertEntity> findByVehicle_Id(Integer vehicleId);
}
