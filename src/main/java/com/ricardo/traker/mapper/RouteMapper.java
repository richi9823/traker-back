package com.ricardo.traker.mapper;

import com.ricardo.traker.model.dto.response.RouteResponseDto;
import com.ricardo.traker.model.dto.response.RouteShortResponseDto;
import com.ricardo.traker.model.entity.RouteEntity;
import org.mapstruct.AfterMapping;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring", uses = {PositionMapper.class})
public interface RouteMapper {

    RouteResponseDto mapEntityToResponse(RouteEntity route);

    @Mapping(target = "init", source = "start")
    RouteShortResponseDto mapEntityToShortResponse(RouteEntity route);

}
