package com.ricardo.traker.repository;

import com.ricardo.traker.model.entity.GPSEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface GPSRepository extends JpaRepository<GPSEntity, Long> {

    List<GPSEntity> findByVehicle_Id(long id);
}
