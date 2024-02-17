package com.ricardo.traker.model.dto.request;

import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class UserEditRequestDto {

    private String firstname;

    private String lastname;

    private String nickname;

    private String email;


}
