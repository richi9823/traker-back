package com.ricardo.traker.model.dto.response;

import lombok.AllArgsConstructor;
import lombok.Data;
@Data
@AllArgsConstructor
public class UserResponseDto {

    private Integer id;

    private String firstname;

    private String lastname;

    private String nickname;

    private String email;

}
