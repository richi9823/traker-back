package com.ricardo.traker.repository;

import com.ricardo.traker.model.entity.GPSEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface GPSRepository extends JpaRepository<GPSEntity, Integer> {
}
