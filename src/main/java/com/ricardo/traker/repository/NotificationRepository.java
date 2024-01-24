package com.ricardo.traker.repository;

import com.ricardo.traker.model.dto.response.NotificationResponseDto;
import com.ricardo.traker.model.entity.NotificationEntity;
import com.ricardo.traker.model.entity.VehicleEntity;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.util.List;

public interface NotificationRepository extends JpaRepository<NotificationEntity, Integer>, JpaSpecificationExecutor<NotificationEntity> {

    List<NotificationEntity> findByAlert_Vehicle_User_Id(Integer userId);

    List<NotificationEntity> findByAlert_Vehicle_User_IdAndReadFalse(Integer userId);

    List<NotificationEntity> findByAlert_Vehicle_Id(Integer vehicleId);

    List<NotificationEntity> findByAlert_Id(Integer alertId);

    static Specification<NotificationEntity> userIs(Integer userId){
        return (root, query, criteriaBuilder)
                ->  criteriaBuilder.equal(root.get("alert").get("vehicle").get("user").get("id"), userId);
    }

    static Specification<NotificationEntity> vehicleIs(Integer vehicleId){
        return (root, query, criteriaBuilder)
                ->  criteriaBuilder.equal(root.get("alert").get("vehicle").get("id"), vehicleId);
    }

    static Specification<NotificationEntity> alertIs(Integer alertId){
        return (root, query, criteriaBuilder)
                ->  criteriaBuilder.equal(root.get("alert").get("id"), alertId);
    }

    static Specification<NotificationEntity> readIs(Boolean read){
        return (root, query, criteriaBuilder)
                ->  criteriaBuilder.equal(root.get("read"), read);
    }
}
