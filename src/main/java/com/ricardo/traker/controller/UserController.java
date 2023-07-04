package com.ricardo.traker.controller;

import com.ricardo.traker.exception.UserException;
import com.ricardo.traker.model.dto.request.UserRequestDto;
import com.ricardo.traker.model.dto.response.UserResponseDto;
import com.ricardo.traker.security.AuthCredentials;
import com.ricardo.traker.security.TokenUtils;
import com.ricardo.traker.security.UserDetailsImpl;
import com.ricardo.traker.service.UserService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.RestController;


@RestController
public class UserController implements UserApi{

    @Autowired
    UserService userService;

    @Autowired
    AuthenticationManager authenticationManager;

    @Autowired
    TokenUtils tokenUtils;

    private final HttpServletRequest request;

    @Autowired
    public UserController(HttpServletRequest request){
        this.request = request;
    }

    @Override
    public ResponseEntity<UserResponseDto> login(@Valid AuthCredentials authCredentials) {
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(authCredentials.getNickname(), authCredentials.getPassword()));

        SecurityContextHolder.getContext().setAuthentication(authentication);
        String jwt = tokenUtils.createToken(authentication);

        UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();

        HttpHeaders responseHeaders = new HttpHeaders();
        responseHeaders.set("Authorization",
                "Bearer " + jwt);

        return ResponseEntity.ok()
                .headers(responseHeaders)
                .body(new UserResponseDto(
                userDetails.getId(),
                userDetails.getFirstname(),
                userDetails.getLastname(),
                userDetails.getUsername(),
                userDetails.getEmail()));
    }

    @Override
    public ResponseEntity<UserResponseDto> signup(@Valid UserRequestDto userRequestDto) throws UserException {
        return ResponseEntity.ok()
                .body(userService.createUser(userRequestDto));
    }

    @Override
    public ResponseEntity<UserResponseDto> session() {
        UserDetailsImpl userDetails = tokenUtils.getUser(request);
        if(userDetails == null) return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        return ResponseEntity.ok()
                .body(new UserResponseDto(
                        userDetails.getId(),
                        userDetails.getFirstname(),
                        userDetails.getLastname(),
                        userDetails.getUsername(),
                        userDetails.getEmail()));
    }

}
