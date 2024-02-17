package com.ricardo.traker.mapper;

import com.ricardo.traker.model.dto.response.GPSResponseDto;
import com.ricardo.traker.model.dto.response.GPSShortResponseDto;
import com.ricardo.traker.model.entity.GPSEntity;
import com.ricardo.traker.traccar.Device;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface GPSMapper {

    @Mapping(target = "registerDeviceId", source = "uniqueId")
    @Mapping(target = "traccarDeviceId", source = "id")
    @Mapping(target = "traccarStatus", source = "status")
    GPSEntity mapDeviceToGpsEntity(Device device);

    GPSResponseDto mapEntityToResponse(GPSEntity gpsEntity);

    GPSShortResponseDto mapEntityToShortResponse(GPSEntity gpsEntity);


    GPSShortResponseDto mapResponseToShortResponse(GPSResponseDto gpsResponseDto);
}
