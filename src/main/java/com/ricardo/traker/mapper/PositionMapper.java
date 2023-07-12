package com.ricardo.traker.mapper;

import com.ricardo.traker.model.dto.PositionsWebSocket;
import com.ricardo.traker.model.dto.response.PositionsResponseDto;
import com.ricardo.traker.model.entity.PositionEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;

@Mapper(componentModel = "spring")
public abstract class PositionMapper {

    @Mapping(target = "time", source = "serverTime")
    public abstract PositionEntity mapPositionWebSocketToPositionEntity (PositionsWebSocket positionsWebSocket);

    public abstract PositionsResponseDto mapPositionEntityToPositionResponse (PositionEntity positionEntity);

    LocalDateTime mapDateToLocalDateTime(Date date){
        return LocalDateTime.ofInstant(date.toInstant(), ZoneId.systemDefault());
    }
}
