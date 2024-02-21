package com.ricardo.traker.mapper;

import com.ricardo.traker.model.dto.PositionsWebSocket;
import com.ricardo.traker.model.dto.response.FullPositionResponseDto;
import com.ricardo.traker.model.dto.response.PositionResponseDto;
import com.ricardo.traker.model.entity.PositionEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.time.OffsetDateTime;
import java.time.ZoneId;
import java.util.Date;

@Mapper(componentModel = "spring", uses = {GPSDeviceMapper.class})
public abstract class PositionMapper {

    @Mapping(target = "time", source = "serverTime")
    public abstract PositionEntity mapPositionWebSocketToPositionEntity (PositionsWebSocket positionsWebSocket);

    public abstract PositionResponseDto mapPositionEntityToPositionResponse (PositionEntity positionEntity);

    @Mapping(target = "gps", source = "route.gps")
    public abstract FullPositionResponseDto mapPositionEntityToFullPositionResponse (PositionEntity positionEntity);

    OffsetDateTime mapDateToLocalDateTime(Date date){
        return OffsetDateTime.ofInstant(date.toInstant(), ZoneId.systemDefault());
    }
}
