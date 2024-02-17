package com.ricardo.traker.repository;

import com.ricardo.traker.enums.IntervalEnum;
import com.ricardo.traker.model.entity.PositionEntity;
import com.ricardo.traker.model.entity.RouteEntity;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

public interface RouteRepository extends JpaRepository<RouteEntity, Long>, JpaSpecificationExecutor<RouteEntity> {

    List<RouteEntity> findByGps_TraccarDeviceId(long id);

    Optional<RouteEntity> findOneByGps_TraccarDeviceIdAndFinishIsNullOrderByStartDesc(long id);

    static Specification<RouteEntity> hasVehicle(Long vehicleId) {
        return (route, query, criteriaBuilder) -> criteriaBuilder.equal(route.get("gps").get("vehicle").get("id"), vehicleId);
    }

    static Specification<RouteEntity> hasInterval(LocalDateTime since, IntervalEnum intervalEnum) {
        return (route, query, criteriaBuilder) -> {
            LocalDateTime sinceTime = since;
            LocalDateTime tillTime = null;

            switch (intervalEnum) {
                case _30MIN -> tillTime = sinceTime.minusMinutes(30);
                case _1H -> tillTime = sinceTime.minusHours(1);
                case _3H -> tillTime = sinceTime.minusHours(3);
                case _12H -> tillTime = sinceTime.minusHours(12);
                case _24H_ -> tillTime = sinceTime.minusDays(1);
                case _3D -> tillTime = sinceTime.minusDays(3);
                case _1W -> tillTime = sinceTime.minusWeeks(1);
                case _1M -> tillTime = sinceTime.minusMonths(1);
                case _1Y -> tillTime = sinceTime.minusYears(1);
            }
            return criteriaBuilder.or(criteriaBuilder.between(route.get("start"), sinceTime, tillTime), criteriaBuilder.between(route.get("finish"), sinceTime, tillTime));
        };
    }
}
