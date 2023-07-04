package com.ricardo.traker.repository;

import com.ricardo.traker.model.entity.ImageEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ImageRepository extends JpaRepository<ImageEntity, String> {
}
