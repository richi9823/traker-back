package com.ricardo.traker.repository;


import com.ricardo.traker.model.entity.RouteEntity;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.time.LocalDate;
import java.time.OffsetDateTime;
import java.time.ZoneOffset;
import java.util.List;
import java.util.Optional;

public interface RouteRepository extends JpaRepository<RouteEntity, Long>, JpaSpecificationExecutor<RouteEntity> {

    List<RouteEntity> findByGps_TraccarDeviceId(long id);

    Optional<RouteEntity> findOneByGps_TraccarDeviceIdAndFinishIsNullOrderByStartDesc(long id);

    static Specification<RouteEntity> hasVehicle(Long vehicleId) {
        return (route, query, criteriaBuilder) -> criteriaBuilder.equal(route.get("gps").get("vehicle").get("id"), vehicleId);
    }

    static Specification<RouteEntity> hasInterval(LocalDate since, LocalDate until) {
        return (route, query, criteriaBuilder) ->
             criteriaBuilder.or(criteriaBuilder.between(route.get("start"), OffsetDateTime.of(since.atStartOfDay(), ZoneOffset.UTC), OffsetDateTime.of(until.plusDays(1).atStartOfDay(), ZoneOffset.UTC)), criteriaBuilder.between(route.get("finish"), OffsetDateTime.of(since.atStartOfDay(), ZoneOffset.UTC), OffsetDateTime.of(until.plusDays(1).atStartOfDay(), ZoneOffset.UTC)));
    }
}
