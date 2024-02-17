package com.ricardo.traker.mapper;

import com.ricardo.traker.model.dto.request.VehicleRequestDto;
import com.ricardo.traker.model.dto.response.VehicleResponseDto;
import com.ricardo.traker.model.dto.response.VehicleShortResponseDto;
import com.ricardo.traker.model.entity.VehicleEntity;
import org.mapstruct.BeanMapping;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;

@Mapper(componentModel = "spring", uses = {GPSMapper.class, AlertMapper.class})
public interface VehicleMapper {

    VehicleEntity mapVehicleRequestToVehicleEntity(VehicleRequestDto vehicleRequestDto);

    @BeanMapping(nullValuePropertyMappingStrategy=NullValuePropertyMappingStrategy.IGNORE)
    VehicleEntity mapVehicleRequestToVehicleEntity(VehicleRequestDto vehicleRequestDto, @MappingTarget VehicleEntity vehicleEntity);

    VehicleResponseDto mapVehicleEntityToVehicleResponseDto(VehicleEntity vehicleEntity);

    VehicleShortResponseDto mapVehicleEntityToVehicleShortResponseDto(VehicleEntity vehicleEntity);


}
