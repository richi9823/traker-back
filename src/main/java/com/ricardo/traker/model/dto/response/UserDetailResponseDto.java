package com.ricardo.traker.model.dto.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;
import lombok.Data;

import java.util.Set;

@Data
@Builder
public class UserDetailResponseDto {

    private Long id;

    private String firstname;

    private String lastname;

    private String nickname;

    private String email;

    @JsonProperty("unread_notifications")
    private Set<NotificationResponseDto> unreadNotifications;
}
