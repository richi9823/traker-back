package com.ricardo.traker.service;

import com.ricardo.traker.exception.ServiceException;
import com.ricardo.traker.exception.TrakerException;
import com.ricardo.traker.mapper.GPSDeviceMapper;
import com.ricardo.traker.mapper.VehicleMapper;
import com.ricardo.traker.model.dto.request.GPSDeviceRequestDto;
import com.ricardo.traker.model.dto.request.VehicleRequestDto;
import com.ricardo.traker.model.dto.response.GPSResponseDto;
import com.ricardo.traker.model.dto.response.ListResponse;
import com.ricardo.traker.model.dto.response.VehicleResponseDto;
import com.ricardo.traker.model.dto.response.VehicleShortResponseDto;
import com.ricardo.traker.model.entity.ImageEntity;
import com.ricardo.traker.model.entity.VehicleEntity;
import com.ricardo.traker.repository.UserRepository;
import com.ricardo.traker.repository.VehicleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.server.ResponseStatusException;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class VehicleService {

    @Autowired
    VehicleRepository vehicleRepository;

    @Autowired
    VehicleMapper vehicleMapper;

    @Autowired
    GPSService gpsService;

    @Autowired
    GPSDeviceMapper gpsMapper;

    @Autowired
    UserRepository userRepository;

    @Autowired
    ImageService imageService;

    @Autowired
    AlertService alertService;



    public VehicleResponseDto createVehicle(VehicleRequestDto vehicleRequestDto, Long userId) throws ServiceException, TrakerException {
        if(vehicleRepository.existsByLicense(vehicleRequestDto.getLicense())){
            throw new TrakerException("Ya existe un vehiculo con esa matricula", HttpStatus.CONFLICT);
        }
        VehicleEntity vehicleEntity = vehicleMapper.mapVehicleRequestToVehicleEntity(vehicleRequestDto);
        vehicleEntity.setUser(userRepository.findById(userId).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "User not found")));
        vehicleEntity = vehicleRepository.save(vehicleEntity);
        var response = vehicleMapper.mapVehicleEntityToVehicleResponseDto(
                vehicleEntity
        );
        if(response.getGps() == null){
            response.setGps(new ArrayList<>());
        }
        if(vehicleRequestDto.getDeviceRequestDto().isPresent()){

            response.getGps().add(gpsMapper.mapResponseToShortResponse(gpsService.createGPS(vehicleEntity, vehicleRequestDto.getDeviceRequestDto().get())));
        }
        return response;
    }


    public VehicleResponseDto editVehicle(Long vehicleId, VehicleRequestDto vehicleRequestDto) throws ServiceException, TrakerException {

        VehicleEntity vehicleEntity = this.getVehicleEntity(vehicleId).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Vehicle not found"));
        if(vehicleRequestDto.getLicense() != null && !vehicleRequestDto.getLicense().equals(vehicleEntity.getLicense()) && vehicleRepository.existsByLicense(vehicleRequestDto.getLicense())){
            throw new TrakerException("Ya existe un vehiculo con esa matricula", HttpStatus.CONFLICT);
        }
        vehicleEntity = vehicleMapper.mapVehicleRequestToVehicleEntity(vehicleRequestDto, vehicleEntity);
        var response = vehicleMapper.mapVehicleEntityToVehicleResponseDto(
                vehicleRepository.save(vehicleEntity));

        if(vehicleRequestDto.getDeviceRequestDto().isPresent()){
            response.getGps().add(gpsMapper.mapResponseToShortResponse(gpsService.createGPS(vehicleEntity, vehicleRequestDto.getDeviceRequestDto().get())));
        }

        return response;
    }


    public VehicleResponseDto getVehicle(Long vehicleId) {
        VehicleEntity vehicleEntity = this.getVehicleEntity(vehicleId).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Vehicle not found"));
        return vehicleMapper.mapVehicleEntityToVehicleResponseDto(vehicleEntity);
    }


    public ListResponse<VehicleShortResponseDto> getUserVehicles(Long userId, Integer page, Integer size, String sort) {
        Page<VehicleEntity> result = vehicleRepository.findAll(Specification.where(VehicleRepository.userIs(userId)), PageRequest.of(page, size, Sort.by(Sort.Direction.DESC, sort)));
        ListResponse<VehicleShortResponseDto> response = new ListResponse<>();
        response.setItems(result.get().map(vehicleMapper::mapVehicleEntityToVehicleShortResponseDto)
                .collect(Collectors.toList()));
        response.setTotal(result.getTotalElements());
        return response;
    }


    public Optional<VehicleEntity> getVehicleEntity(Long vehicleId) {
        return vehicleRepository.findById(vehicleId);
    }

    public VehicleResponseDto setImage(Long id, MultipartFile image) throws ServiceException {
        VehicleEntity vehicleEntity = this.getVehicleEntity(id).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Vehicle not found"));
        try {
            ImageEntity oldImage = null;
            if(vehicleEntity.getImage() != null){
                oldImage = imageService.getImageById(vehicleEntity.getImage().getId());
            }
            ImageEntity imageEntity = imageService.uploadImage(image);
            vehicleEntity.setImage(imageEntity);
            var response = vehicleMapper.mapVehicleEntityToVehicleResponseDto(
                    vehicleRepository.save(vehicleEntity)
            );
            if(oldImage != null){
                imageService.deleteImage(oldImage.getId());
            }
            return response;
        } catch (IOException e) {
            throw new ServiceException("I/O exception", e);
        }
    }

    public VehicleResponseDto deleteImage(Long id) {
        VehicleEntity vehicleEntity = this.getVehicleEntity(id).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Vehicle not found"));

            if(vehicleEntity.getImage() != null){
                ImageEntity oldImage = imageService.getImageById(vehicleEntity.getImage().getId());
                vehicleEntity.setImage(null);
                var response = vehicleMapper.mapVehicleEntityToVehicleResponseDto(
                        vehicleRepository.save(vehicleEntity));
                imageService.deleteImage(oldImage.getId());
                return response;
            }else{
                throw new ResponseStatusException(HttpStatus.CONFLICT, "The vehicle has'nt image");
            }

    }

    public void deleteByUserId(long id){
        var vehicleList = vehicleRepository.findByUser_Id(id);
        for(var vehicle: vehicleList){
            this.deleteById(vehicle.getId());
        }
    }

    public void deleteById(long id){
        alertService.deleteNotificationsByVehicleId(id);
        gpsService.deleteByVehicleId(id);
        vehicleRepository.deleteById(id);
    }

    public GPSResponseDto addGpsDevice(Long vehicleId, GPSDeviceRequestDto gpsDeviceRequestDto) throws ServiceException, TrakerException {
        VehicleEntity vehicleEntity = this.getVehicleEntity(vehicleId).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Vehicle not found"));
        return gpsService.createGPS(vehicleEntity, gpsDeviceRequestDto);
    }


}
