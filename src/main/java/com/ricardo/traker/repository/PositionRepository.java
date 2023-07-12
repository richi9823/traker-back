package com.ricardo.traker.repository;

import com.ricardo.traker.enums.IntervalEnum;
import com.ricardo.traker.model.entity.PositionEntity;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.time.LocalDateTime;
import java.util.List;

public interface PositionRepository extends JpaRepository<PositionEntity, Integer>, JpaSpecificationExecutor<PositionEntity> {

    List<PositionEntity> findByGps_TraccarDeviceIdOrderByTimeDesc(Integer gpsId);

    static Specification<PositionEntity> hasVehicle(Integer vehicleId) {
        return (position, query, criteriaBuilder) -> criteriaBuilder.equal(position.get("gps").get("vehicle").get("id"), vehicleId);
    }

    static Specification<PositionEntity> hasInterval(String since, IntervalEnum intervalEnum) {
        return (position, query, criteriaBuilder) -> {
            LocalDateTime sinceTime = LocalDateTime.parse(since);
            LocalDateTime tillTime = sinceTime;
            switch (intervalEnum) {
                case _30MIN -> tillTime = sinceTime.plusMinutes(30);
                case _1H -> tillTime = sinceTime.plusHours(1);
                case _3H -> tillTime = sinceTime.plusHours(3);
                case _12H -> tillTime = sinceTime.plusHours(12);
                case _24H_ -> tillTime = sinceTime.plusDays(1);
                case _3D -> tillTime = sinceTime.plusDays(3);
                case _1W -> tillTime = sinceTime.plusWeeks(1);
                case _1M -> tillTime = sinceTime.plusMonths(1);
                case _1Y -> tillTime = sinceTime.plusYears(1);
            }
            return criteriaBuilder.between(position.get("time"), sinceTime, tillTime);
        };
    }
}
