package com.ricardo.traker.mapper;

import com.ricardo.traker.model.entity.GPSEntity;
import com.ricardo.traker.traccar.Device;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface GPSMapper {

    @Mapping(target = "registerDeviceId", source = "uniqueId")
    @Mapping(target = "traccarDeviceId", source = "id")
    GPSEntity mapDeviceToGpsEntity(Device device);
}
