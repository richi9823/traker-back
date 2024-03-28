package com.ricardo.traker.repository;

import com.ricardo.traker.model.entity.AlertEntity.AlertEntity;
import com.ricardo.traker.model.entity.VehicleEntity;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.util.List;
import java.util.Optional;

public interface VehicleRepository extends JpaRepository<VehicleEntity, Long>, JpaSpecificationExecutor<VehicleEntity> {

    Optional<VehicleEntity> findByIdAndUser_Id(Long id, Long userId);

    static Specification<VehicleEntity> userIs(Long userId){
        return (root, query, criteriaBuilder)
                ->  criteriaBuilder.equal(root.get("user").get("id"), userId);
    }

    List<VehicleEntity> findByUser_Id(Long id);

    boolean existsByLicense(String license);
}
