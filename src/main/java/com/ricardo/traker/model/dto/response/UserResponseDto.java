package com.ricardo.traker.model.dto.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
@Data
@AllArgsConstructor
public class UserResponseDto {

    private Long id;

    private String firstname;

    private String lastname;

    private String nickname;

    private String email;

    @JsonProperty("access_token")
    private String accessToken;

}
