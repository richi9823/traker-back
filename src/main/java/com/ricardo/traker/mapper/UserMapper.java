package com.ricardo.traker.mapper;

import com.ricardo.traker.model.dto.request.UserEditRequestDto;
import com.ricardo.traker.model.dto.request.UserRequestDto;
import com.ricardo.traker.model.dto.response.UserDetailResponseDto;
import com.ricardo.traker.model.dto.response.UserResponseDto;
import com.ricardo.traker.model.entity.UserEntity;
import org.mapstruct.BeanMapping;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;

@Mapper(componentModel = "spring")
public interface UserMapper {

    UserEntity mapUserRequestDtoToUserEntity(UserRequestDto userRequestDto);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    UserEntity updateUserEntity(UserEditRequestDto userRequestDto, @MappingTarget UserEntity userEntity);

    UserResponseDto mapUserEntityToUserResponseDto(UserEntity userEntity);

    UserDetailResponseDto mapUserEntityToUserDetailResponseDto(UserEntity userEntity);

}
