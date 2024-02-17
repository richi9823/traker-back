package com.ricardo.traker.controller;

import com.ricardo.traker.model.dto.request.UserEditRequestDto;
import com.ricardo.traker.model.dto.request.UserRequestDto;
import com.ricardo.traker.model.dto.response.UserDetailResponseDto;
import com.ricardo.traker.model.dto.response.UserResponseDto;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@RequestMapping("/user")
@SecurityRequirement(name = "Bearer Authentication")
public interface UserApi {

    @RequestMapping(value = "", method = RequestMethod.GET)
    ResponseEntity<UserDetailResponseDto> getUserDetails();

    @RequestMapping(value = "", method = RequestMethod.PUT)
    ResponseEntity<UserDetailResponseDto> editUser(UserEditRequestDto userEditRequestDto);

    @RequestMapping(value = "", method = RequestMethod.DELETE)
    ResponseEntity<Void> deleteUser();
}
