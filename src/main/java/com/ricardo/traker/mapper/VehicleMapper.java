package com.ricardo.traker.mapper;

import com.ricardo.traker.model.dto.request.VehicleRequestDto;
import com.ricardo.traker.model.dto.response.VehicleResponseDto;
import com.ricardo.traker.model.dto.response.VehicleShortResponseDto;
import com.ricardo.traker.model.entity.VehicleEntity;
import org.mapstruct.*;

@Mapper(componentModel = "spring", uses = {GPSDeviceMapper.class, AlertMapper.class})
public interface VehicleMapper {

    VehicleEntity mapVehicleRequestToVehicleEntity(VehicleRequestDto vehicleRequestDto);

    @BeanMapping(nullValuePropertyMappingStrategy=NullValuePropertyMappingStrategy.IGNORE)
    VehicleEntity mapVehicleRequestToVehicleEntity(VehicleRequestDto vehicleRequestDto, @MappingTarget VehicleEntity vehicleEntity);

    @Mapping(target = "image", source = "image.id")
    VehicleResponseDto mapVehicleEntityToVehicleResponseDto(VehicleEntity vehicleEntity);

    VehicleShortResponseDto mapVehicleEntityToVehicleShortResponseDto(VehicleEntity vehicleEntity);


}
