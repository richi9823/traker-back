package com.ricardo.traker.repository;

import com.ricardo.traker.model.dto.response.NotificationResponseDto;
import com.ricardo.traker.model.entity.AlertEntity.AlertEntity;
import com.ricardo.traker.model.entity.NotificationEntity;
import com.ricardo.traker.model.entity.PositionEntity;
import com.ricardo.traker.model.entity.VehicleEntity;
import jakarta.persistence.criteria.Join;
import jakarta.persistence.criteria.JoinType;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.util.List;

public interface NotificationRepository extends JpaRepository<NotificationEntity, Long>, JpaSpecificationExecutor<NotificationEntity> {

    List<NotificationEntity> findByAlert_Id(Long alertId);


    static Specification<NotificationEntity> userIs(Long userId){
        return (root, query, criteriaBuilder)
                ->  criteriaBuilder.equal(root.get("alert").get("user").get("id"), userId);
    }

    static Specification<NotificationEntity> hasVehicle(Long vehicleId){
        return (root, query, criteriaBuilder)
                ->  {
            Join<NotificationEntity, PositionEntity> join = root.join("positions", JoinType.RIGHT);
            return criteriaBuilder.equal(join.get("route").get("gps").get("vehicle").get("id"), vehicleId);
        };
    }

    static Specification<NotificationEntity> alertIs(Long alertId){
        return (root, query, criteriaBuilder)
                ->  criteriaBuilder.equal(root.get("alert").get("id"), alertId);
    }

    static Specification<NotificationEntity> readIs(Boolean read){
        return (root, query, criteriaBuilder)
                ->  criteriaBuilder.equal(root.get("read"), read);
    }
}
