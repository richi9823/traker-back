package com.ricardo.traker.mapper;

import com.ricardo.traker.model.dto.request.UserRequestDto;
import com.ricardo.traker.model.dto.response.UserResponseDto;
import com.ricardo.traker.model.entity.UserEntity;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface UserMapper {

    UserEntity mapUserRequestDtoToUserEntity(UserRequestDto userRequestDto);

    UserResponseDto mapUserEntityToUserResponseDto(UserEntity userEntity);

}
