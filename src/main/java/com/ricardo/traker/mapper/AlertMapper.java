package com.ricardo.traker.mapper;

import com.ricardo.traker.enums.TypeAlertEnum;
import com.ricardo.traker.model.dto.request.AlertRequest.AlertRequestDto;
import com.ricardo.traker.model.dto.response.AlertResponse.*;
import com.ricardo.traker.model.entity.AlertEntity.AlertArrivalEntity;
import com.ricardo.traker.model.entity.AlertEntity.AlertDistanceEntity;
import com.ricardo.traker.model.entity.AlertEntity.AlertEntity;
import com.ricardo.traker.model.entity.AlertEntity.AlertSpeedEntity;

import org.mapstruct.*;

@Mapper(componentModel = "spring", uses = {VehicleMapper.class})
public abstract class AlertMapper {

    @Mapping(target = "latitude", source = "arrival.latitude")
    @Mapping(target = "longitude", source = "arrival.longitude")
    @Mapping(target = "vehicles", ignore = true)
    public  abstract AlertArrivalEntity mapAlertRequestDtoToAlertArrivalEntity(AlertRequestDto alertRequestDto);

    @Mapping(target = "maxDistance", source = "distance.maxDistance")
    @Mapping(target = "pointReferenceLatitude", source = "distance.pointReferenceLatitude")
    @Mapping(target = "pointReferenceLongitude", source = "distance.pointReferenceLongitude")
    @Mapping(target = "vehicles", ignore = true)
    public  abstract AlertDistanceEntity mapAlertRequestDtoToAlertDistanceEntity(AlertRequestDto alertRequestDto);

    @Mapping(target = "speedLimit", source = "speed.speedLimit")
    @Mapping(target = "vehicles", ignore = true)
    public  abstract AlertSpeedEntity mapAlertRequestDtoToAlertSpeedEntity(AlertRequestDto alertRequestDto);

    @Mapping(target = "vehicles", ignore = true)
    public  abstract AlertEntity mapAlertRequestDtoToAlertEntity(AlertRequestDto alertRequestDto);

    @AfterMapping
    AlertEntity afterMapAlertRequestDtoToAlertEntity(AlertRequestDto alertRequestDto, @MappingTarget AlertEntity.AlertEntityBuilder alertEntity) {
        return switch (alertRequestDto.getType()){
            case SPEED -> this.mapAlertRequestDtoToAlertSpeedEntity(alertRequestDto);
            case ARRIVAL -> this.mapAlertRequestDtoToAlertArrivalEntity(alertRequestDto);
            case  DISTANCE -> this.mapAlertRequestDtoToAlertDistanceEntity(alertRequestDto);
        };
    }

    @Mapping(target = "latitude", source = "arrival.latitude")
    @Mapping(target = "longitude", source = "arrival.longitude")
    @Mapping(target = "vehicles", ignore = true)
    @Mapping(target = "type", ignore = true)
    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    public  abstract  AlertArrivalEntity mapAlertRequestDtoToAlertArrivalEntity(AlertRequestDto alertRequestDto, @MappingTarget AlertArrivalEntity alertEntity);

    @Mapping(target = "maxDistance", source = "distance.maxDistance")
    @Mapping(target = "vehicles", ignore = true)
    @Mapping(target = "type", ignore = true)
    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    public  abstract AlertDistanceEntity mapAlertRequestDtoToAlertDistanceEntity(AlertRequestDto alertRequestDto, @MappingTarget AlertDistanceEntity alertEntity);

    @Mapping(target = "speedLimit", source = "speed.speedLimit")
    @Mapping(target = "vehicles", ignore = true)
    @Mapping(target = "type", ignore = true)
    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    public  abstract  AlertSpeedEntity mapAlertRequestDtoToAlertSpeedEntity(AlertRequestDto alertRequestDto, @MappingTarget AlertSpeedEntity alertEntity);

    public  abstract  AlertResponseDto mapAlertEntityToAlertResponseDto(AlertEntity alertEntity);

    public  abstract AlertShortResponseDto mapAlertEntityToAlertShortResponseDto(AlertEntity alertEntity);

    @AfterMapping
    AlertResponseDto mapAlertEntityToAlertResponseDto(AlertEntity alertEntity, @MappingTarget AlertResponseDto alertResponseDto) {

        if(alertEntity instanceof AlertSpeedEntity){
            alertResponseDto.setType(TypeAlertEnum.SPEED);
            alertResponseDto.setSpeed(new AlertSpeedResponseDto(((AlertSpeedEntity) alertEntity).getSpeedLimit()));
        }

        if(alertEntity instanceof AlertArrivalEntity){
            alertResponseDto.setType(TypeAlertEnum.ARRIVAL);
            alertResponseDto.setArrival(new AlertArrivalResponseDto(((AlertArrivalEntity) alertEntity).getLatitude(),((AlertArrivalEntity) alertEntity).getLongitude() ));
        }

        if(alertEntity instanceof AlertDistanceEntity){
            alertResponseDto.setType(TypeAlertEnum.DISTANCE);
            alertResponseDto.setDistance(new AlertDistanceResponseDto(((AlertDistanceEntity) alertEntity).getMaxDistance()));
        }
        return alertResponseDto;

    }


}
