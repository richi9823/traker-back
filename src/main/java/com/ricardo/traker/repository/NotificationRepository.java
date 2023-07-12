package com.ricardo.traker.repository;

import com.ricardo.traker.model.dto.response.NotificationResponseDto;
import com.ricardo.traker.model.entity.NotificationEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface NotificationRepository extends JpaRepository<NotificationEntity, Integer> {

    List<NotificationEntity> findByAlert_Vehicle_User_Id(Integer userId);

    List<NotificationEntity> findByAlert_Vehicle_User_IdAndReadFalse(Integer userId);

    List<NotificationEntity> findByAlert_Vehicle_Id(Integer vehicleId);

    List<NotificationEntity> findByAlert_Id(Integer alertId);
}
