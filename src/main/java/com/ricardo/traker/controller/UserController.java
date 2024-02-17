package com.ricardo.traker.controller;

import com.ricardo.traker.mapper.UserMapper;
import com.ricardo.traker.model.dto.request.UserEditRequestDto;
import com.ricardo.traker.model.dto.response.UserDetailResponseDto;
import com.ricardo.traker.security.TokenUtils;
import com.ricardo.traker.security.UserDetailsImpl;
import com.ricardo.traker.service.UserService;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

@RestController
public class UserController implements UserApi{

    @Autowired
    TokenUtils tokenUtils;

    @Autowired
    UserService userService;

    @Autowired
    UserMapper userMapper;


    private final HttpServletRequest request;

    @Autowired
    public UserController(HttpServletRequest request){
        this.request = request;
    }
    @Override
    public ResponseEntity<UserDetailResponseDto> getUserDetails() {
        UserDetailsImpl userDetails = tokenUtils.getUser(request);
        if(userDetails == null) return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        return ResponseEntity.ok().body(userMapper.mapUserEntityToUserDetailResponseDto(
                userService.getUserEntity(userDetails.getId()).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "User " + userDetails.getId() + "Not found")))
        );
    }

    @Override
    public ResponseEntity<UserDetailResponseDto> editUser(UserEditRequestDto userEditRequestDto) {
        UserDetailsImpl userDetails = tokenUtils.getUser(request);
        if(userDetails == null) return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        return ResponseEntity.ok().body(userService.editUser(userDetails.getId(), userEditRequestDto));
    }

    @Override
    public ResponseEntity<Void> deleteUser() {
        UserDetailsImpl userDetails = tokenUtils.getUser(request);
        if(userDetails == null) return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        userService.deleteById(userDetails.getId());
        return ResponseEntity.ok().build();
    }
}
