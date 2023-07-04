package com.ricardo.traker.controller;

import com.ricardo.traker.exception.UserException;
import com.ricardo.traker.model.dto.request.UserRequestDto;
import com.ricardo.traker.model.dto.response.UserResponseDto;
import com.ricardo.traker.security.AuthCredentials;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@RequestMapping("/api/user")
public interface UserApi {

    @RequestMapping(value = "/login", method = RequestMethod.POST)
    ResponseEntity<UserResponseDto> login(@RequestBody @Valid AuthCredentials authCredentials);

    @RequestMapping(value = "", method = RequestMethod.POST)
    ResponseEntity<UserResponseDto> signup(@RequestBody @Valid UserRequestDto userRequestDto) throws UserException;

    @RequestMapping(value = "/session", method = RequestMethod.GET)
    ResponseEntity<UserResponseDto> session();

}
