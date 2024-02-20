package com.ricardo.traker.repository;

import com.ricardo.traker.model.entity.AlertEntity.AlertEntity;
import com.ricardo.traker.model.entity.VehicleEntity;
import jakarta.persistence.criteria.Join;
import jakarta.persistence.criteria.JoinType;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.util.List;

public interface AlertRepository extends JpaRepository<AlertEntity, Long>, JpaSpecificationExecutor<AlertEntity> {

    List<AlertEntity> findByUser_Id(Long userId);
    static Specification<AlertEntity> userIs(Long userId){
        return (root, query, criteriaBuilder)
                ->  criteriaBuilder.equal(root.get("user").get("id"), userId);
    }

    static Specification<AlertEntity> hasVehicle(Long vehicleId){
        return (root, query, criteriaBuilder)
                ->  {
                    Join<AlertEntity, VehicleEntity> join = root.join("vehicles", JoinType.RIGHT);
                    return criteriaBuilder.equal(join.get("id"), vehicleId);
        };
    }
}
