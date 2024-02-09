package com.ricardo.traker.repository;

import com.ricardo.traker.model.entity.AlertEntity.AlertEntity;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.util.List;

public interface AlertRepository extends JpaRepository<AlertEntity, Long>, JpaSpecificationExecutor<AlertEntity> {

    List<AlertEntity> findByVehicle_Id(Long vehicleId);

    static Specification<AlertEntity> vehicleIs(Long vehicleId){
        return (root, query, criteriaBuilder)
                ->  criteriaBuilder.equal(root.get("vehicle").get("id"), vehicleId);
    }
}
