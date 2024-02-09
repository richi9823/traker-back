package com.ricardo.traker.model.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;
import java.time.OffsetDateTime;
import java.util.List;
@Data
@AllArgsConstructor
@Builder
public class RoutesResponseDto {

    OffsetDateTime init;
    OffsetDateTime finish;
    List<PositionsResponseDto> positions;
}
