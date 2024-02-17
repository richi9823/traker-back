package com.ricardo.traker.mapper;

import com.ricardo.traker.model.dto.response.NotificationResponseDto;
import com.ricardo.traker.model.dto.response.NotificationShortResponseDto;
import com.ricardo.traker.model.entity.NotificationEntity;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring", uses = {PositionMapper.class, AlertMapper.class})
public interface NotificationMapper {

    NotificationResponseDto mapNotificationEntityToNotificationResponseDto(NotificationEntity notificationEntity);

    NotificationShortResponseDto mapNotificationEntityToNotificationShortResponseDto(NotificationEntity notificationEntity);
}
