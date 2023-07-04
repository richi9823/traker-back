package com.ricardo.traker.model.dto.request;

import jakarta.validation.constraints.NotNull;
import lombok.Data;
import org.springframework.validation.annotation.Validated;

@Data
@Validated
public class UserRequestDto {

    @NotNull
    private String firstname;

    @NotNull
    private String lastname;

    @NotNull
    private String nickname;

    @NotNull
    private String email;

    @NotNull
    private String password;

}
