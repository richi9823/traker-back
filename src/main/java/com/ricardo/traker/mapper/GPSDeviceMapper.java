package com.ricardo.traker.mapper;

import com.ricardo.traker.model.dto.response.GPSResponseDto;
import com.ricardo.traker.model.dto.response.GPSShortResponseDto;
import com.ricardo.traker.model.entity.GPSEntity;
import com.ricardo.traker.traccar.Device;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface GPSDeviceMapper {

    @Mapping(target = "registerDeviceId", source = "uniqueId")
    @Mapping(target = "traccarDeviceId", source = "id")
    @Mapping(target = "traccarStatus", source = "status")
    @Mapping(target = "status", ignore = true)
    GPSEntity mapDeviceToGpsEntity(Device device);

    @Mapping(target = "id", source = "traccarDeviceId")
    GPSResponseDto mapEntityToResponse(GPSEntity gpsEntity);

    @Mapping(target = "id", source = "traccarDeviceId")
    GPSShortResponseDto mapEntityToShortResponse(GPSEntity gpsEntity);


    GPSShortResponseDto mapResponseToShortResponse(GPSResponseDto gpsResponseDto);
}
