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

    static Specification<PositionEntity> hasInterval(String till, IntervalEnum intervalEnum) {
        return (position, query, criteriaBuilder) -> {
            LocalDateTime tillTime = LocalDateTime.parse(till);
            LocalDateTime sinceTime = tillTime;
            switch (intervalEnum) {
                case _30MIN -> sinceTime = tillTime.minusMinutes(30);
                case _1H -> sinceTime = tillTime.minusHours(1);
                case _3H -> sinceTime = tillTime.minusHours(3);
                case _12H -> sinceTime = tillTime.minusHours(12);
                case _24H_ -> sinceTime = tillTime.minusDays(1);
                case _3D -> sinceTime = tillTime.minusDays(3);
                case _1W -> sinceTime = tillTime.minusWeeks(1);
                case _1M -> sinceTime = tillTime.minusMonths(1);
                case _1Y -> sinceTime = tillTime.minusYears(1);
            }
            return criteriaBuilder.between(position.get("time"), sinceTime, tillTime);
        };
    }
}
