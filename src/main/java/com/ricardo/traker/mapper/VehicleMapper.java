package com.ricardo.traker.mapper;

import com.ricardo.traker.model.dto.request.VehicleRequestDto;
import com.ricardo.traker.model.dto.response.VehicleResponseDto;
import com.ricardo.traker.model.dto.response.VehicleShortResponseDto;
import com.ricardo.traker.model.entity.GPSEntity;
import com.ricardo.traker.model.entity.VehicleEntity;
import org.mapstruct.*;

import java.math.BigDecimal;
import java.util.List;

@Mapper(componentModel = "spring", uses = {GPSDeviceMapper.class})
public abstract class VehicleMapper {

    public abstract VehicleEntity mapVehicleRequestToVehicleEntity(VehicleRequestDto vehicleRequestDto);

    @BeanMapping(nullValuePropertyMappingStrategy=NullValuePropertyMappingStrategy.IGNORE)
    public abstract VehicleEntity mapVehicleRequestToVehicleEntity(VehicleRequestDto vehicleRequestDto, @MappingTarget VehicleEntity vehicleEntity);

    @Mapping(target = "image", source = "image.id")
    @Mapping(target = "totalDistance", source = "gps")
    public abstract VehicleResponseDto mapVehicleEntityToVehicleResponseDto(VehicleEntity vehicleEntity);

    BigDecimal mapDistance(List<GPSEntity> listGPS){
        if(listGPS == null) return BigDecimal.ZERO;

        return listGPS.stream().filter(g->g.getTotalDistance() != null).map(g -> g.getTotalDistance()).reduce(BigDecimal.ZERO, BigDecimal::add);
    }

    public abstract VehicleShortResponseDto mapVehicleEntityToVehicleShortResponseDto(VehicleEntity vehicleEntity);


}
