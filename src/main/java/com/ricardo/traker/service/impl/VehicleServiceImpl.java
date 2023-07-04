package com.ricardo.traker.service.impl;

import com.ricardo.traker.exception.ServiceException;
import com.ricardo.traker.mapper.VehicleMapper;
import com.ricardo.traker.model.dto.request.VehicleRequest;
import com.ricardo.traker.model.dto.response.VehicleResponseDto;
import com.ricardo.traker.model.entity.VehicleEntity;
import com.ricardo.traker.repository.VehicleRepository;
import com.ricardo.traker.service.GPSService;
import com.ricardo.traker.service.UserService;
import com.ricardo.traker.service.VehicleService;
import com.ricardo.traker.traccar.api.DevicesApi;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class VehicleServiceImpl implements VehicleService {

    @Autowired
    VehicleRepository vehicleRepository;

    @Autowired
    UserService userService;

    @Autowired
    VehicleMapper vehicleMapper;

    @Autowired
    GPSService gpsService;


    @Override
    public VehicleResponseDto createVehicle(VehicleRequest vehicleRequest, Integer userId) throws ServiceException {
        VehicleEntity vehicleEntity = vehicleMapper.mapVehicleRequestToVehicleEntity(vehicleRequest);
        vehicleEntity.setGps(gpsService.createGPS(vehicleRequest));
        vehicleEntity.setUser(userService.getUserEntity(userId).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "User not found")));
        return vehicleMapper.mapVehicleEntityToVehicleResponseDto(
                vehicleRepository.save(vehicleEntity)
        );
    }

    @Override
    public VehicleResponseDto editVehicle(Integer vehicleId, VehicleRequest vehicleRequest) {
        VehicleEntity vehicleEntity = this.getVehicleEntity(vehicleId).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Vehicle not found"));
        vehicleEntity = vehicleMapper.mapVehicleRequestToVehicleEntity(vehicleRequest, vehicleEntity);
        return vehicleMapper.mapVehicleEntityToVehicleResponseDto(
                vehicleRepository.save(vehicleEntity)
        );
    }

    @Override
    public VehicleResponseDto removeVehicle(Integer vehicleId) {
        VehicleEntity vehicleEntity = this.getVehicleEntity(vehicleId).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Vehicle not found"));
        vehicleRepository.deleteById(vehicleId);
        return vehicleMapper.mapVehicleEntityToVehicleResponseDto(vehicleEntity);
    }

    @Override
    public List<VehicleResponseDto> getUserVehicles(Integer userId) {
        return vehicleRepository
                .findByUser_Id(userId)
                .stream()
                .map(vehicleMapper::mapVehicleEntityToVehicleResponseDto)
                .collect(Collectors.toList());
    }

    @Override
    public Optional<VehicleEntity> getVehicleEntity(Integer vehicleId) {
        return vehicleRepository.findById(vehicleId);
    }
}
