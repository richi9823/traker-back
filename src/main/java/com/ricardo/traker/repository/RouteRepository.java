package com.ricardo.traker.repository;

import com.ricardo.traker.model.entity.RouteEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface RouteRepository extends JpaRepository<RouteEntity, Long> {

    List<RouteEntity> findByGps_TraccarDeviceId(long id);
}
