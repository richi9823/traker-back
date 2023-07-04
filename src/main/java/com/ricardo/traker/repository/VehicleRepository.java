package com.ricardo.traker.repository;

import com.ricardo.traker.model.entity.VehicleEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface VehicleRepository extends JpaRepository<VehicleEntity, Integer> {

    List<VehicleEntity> findByUser_Id(Integer id);
}
