package com.ricardo.traker.service;

import com.ricardo.traker.exception.UserException;
import com.ricardo.traker.mapper.UserMapper;
import com.ricardo.traker.model.dto.request.UserRequestDto;
import com.ricardo.traker.model.dto.response.UserResponseDto;
import com.ricardo.traker.model.entity.UserEntity;
import com.ricardo.traker.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;


@Service
public class UserService {

    @Autowired
    UserRepository userRepository;

    @Autowired
    UserMapper userMapper;

    @Autowired
    PasswordEncoder passwordEncoder;

    @Autowired
    VehicleService vehicleService;


    public UserResponseDto createUser(UserRequestDto userRequestDto) throws UserException {

        this.validationUser(userRequestDto);
        userRequestDto.setPassword(passwordEncoder.encode(userRequestDto.getPassword()));
        UserEntity user = userRepository.save(userMapper.mapUserRequestDtoToUserEntity(userRequestDto));

        return userMapper.mapUserEntityToUserResponseDto(user);

    }


    public Optional<UserEntity> getUserEntity(Long id) {
        return userRepository.findById(id);
    }

    private void validationUser(UserRequestDto userRequestDto) throws UserException {
        Set<String> errors = new HashSet<>();

        if(userRepository.existsByNickname(userRequestDto.getNickname())){
            errors.add("This already nickname exists - " + userRequestDto.getNickname());
        }
        if(userRepository.existsByEmail(userRequestDto.getEmail())){
            errors.add("This already email exists - " + userRequestDto.getEmail());
        }
        if(!errors.isEmpty()){
            String message = "";
            for(String s: errors){
                message = message.concat(s+";");
            }
            throw new UserException(message);
        }
    }


    void deleteById(long id){
        vehicleService.deleteByUserId(id);
        userRepository.deleteById(id);
    }
}
