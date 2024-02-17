package com.ricardo.traker.mapper;

import com.ricardo.traker.model.dto.response.RouteResponseDto;
import com.ricardo.traker.model.dto.response.RouteShortResponseDto;
import com.ricardo.traker.model.entity.RouteEntity;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring", uses = {PositionMapper.class})
public interface RouteMapper {

    RouteResponseDto mapEntityToResponse(RouteEntity route);

    RouteShortResponseDto mapEntityToShortResponse(RouteEntity route);
}
