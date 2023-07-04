package com.ricardo.traker.service;

import com.ricardo.traker.exception.UserException;
import com.ricardo.traker.model.dto.request.UserRequestDto;
import com.ricardo.traker.model.dto.response.UserResponseDto;
import com.ricardo.traker.model.entity.UserEntity;

import java.util.List;
import java.util.Optional;


public interface UserService {
    UserResponseDto createUser(UserRequestDto userRequestDto) throws UserException;

    Optional<UserEntity> getUserEntity(Integer id);
}
