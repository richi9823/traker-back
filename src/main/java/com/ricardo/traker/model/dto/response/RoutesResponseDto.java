package com.ricardo.traker.model.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.List;
@Data
@AllArgsConstructor
@Builder
public class RoutesResponseDto {

    LocalDateTime init;
    LocalDateTime finish;
    List<PositionsResponseDto> positions;
}
