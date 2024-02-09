package com.ricardo.traker.service;

import com.ricardo.traker.exception.ServiceException;
import com.ricardo.traker.mapper.VehicleMapper;
import com.ricardo.traker.model.dto.request.VehicleRequestDto;
import com.ricardo.traker.model.dto.response.ListResponse;
import com.ricardo.traker.model.dto.response.VehicleResponseDto;
import com.ricardo.traker.model.entity.VehicleEntity;
import com.ricardo.traker.repository.VehicleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class VehicleService {

    @Autowired
    VehicleRepository vehicleRepository;

    @Autowired
    UserService userService;

    @Autowired
    VehicleMapper vehicleMapper;

    @Autowired
    GPSService gpsService;



    public VehicleResponseDto createVehicle(VehicleRequestDto vehicleRequestDto, Long userId) throws ServiceException {
        VehicleEntity vehicleEntity = vehicleMapper.mapVehicleRequestToVehicleEntity(vehicleRequestDto);
        //vehicleEntity.setGps(gpsService.createGPS(vehicleRequestDto));
        vehicleEntity.setUser(userService.getUserEntity(userId).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "User not found")));
        return vehicleMapper.mapVehicleEntityToVehicleResponseDto(
                vehicleRepository.save(vehicleEntity)
        );
    }


    public VehicleResponseDto editVehicle(Long vehicleId, VehicleRequestDto vehicleRequestDto) {
        VehicleEntity vehicleEntity = this.getVehicleEntity(vehicleId).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Vehicle not found"));
        vehicleEntity = vehicleMapper.mapVehicleRequestToVehicleEntity(vehicleRequestDto, vehicleEntity);
        return vehicleMapper.mapVehicleEntityToVehicleResponseDto(
                vehicleRepository.save(vehicleEntity)
        );
    }


    public VehicleResponseDto getVehicle(Long vehicleId) {
        VehicleEntity vehicleEntity = this.getVehicleEntity(vehicleId).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Vehicle not found"));
        return vehicleMapper.mapVehicleEntityToVehicleResponseDto(vehicleEntity);
    }


    public ListResponse<VehicleResponseDto> getUserVehicles(Long userId, Integer page, Integer size, String sort) {
        Page<VehicleEntity> result = vehicleRepository.findAll(Specification.where(VehicleRepository.userIs(userId)), PageRequest.of(page, size, Sort.by(Sort.Direction.DESC, sort)));
        ListResponse<VehicleResponseDto> response = new ListResponse<>();
        response.setItems(result.get().map(vehicleMapper::mapVehicleEntityToVehicleResponseDto)
                .collect(Collectors.toList()));
        response.setTotal(result.getTotalElements());
        return response;
    }


    public Optional<VehicleEntity> getVehicleEntity(Long vehicleId) {
        return vehicleRepository.findById(vehicleId);
    }


    public Optional<VehicleEntity> getVehicleEntityByGpsId(Long gpsId) {
        return vehicleRepository.findByGps_TraccarDeviceId(gpsId);
    }

    public void deleteByUserId(long id){
        var vehicleList = vehicleRepository.findByUser_Id(id);
        for(var vehicle: vehicleList){
            this.deleteById(vehicle.getId());
        }
    }

    public void deleteById(long id){
        gpsService.deleteByVehicleId(id);
        vehicleRepository.deleteById(id);
    }


}
