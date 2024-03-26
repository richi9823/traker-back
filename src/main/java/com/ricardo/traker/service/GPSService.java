package com.ricardo.traker.service;

import com.ricardo.traker.enums.GPSStatusEnum;
import com.ricardo.traker.exception.ServiceException;
import com.ricardo.traker.exception.TrakerException;
import com.ricardo.traker.mapper.GPSDeviceMapper;
import com.ricardo.traker.model.dto.MessageWebSocket;
import com.ricardo.traker.model.dto.request.GPSDeviceRequestDto;
import com.ricardo.traker.model.dto.response.GPSResponseDto;
import com.ricardo.traker.model.dto.response.GPSShortResponseDto;
import com.ricardo.traker.model.entity.GPSEntity;
import com.ricardo.traker.model.entity.VehicleEntity;
import com.ricardo.traker.repository.GPSRepository;
import com.ricardo.traker.traccar.Device;
import com.ricardo.traker.traccar.api.DevicesApi;
import jakarta.transaction.Transactional;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClientResponseException;
import org.springframework.web.server.ResponseStatusException;
import reactor.core.publisher.Mono;

import java.time.OffsetDateTime;
import java.time.ZoneId;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@Slf4j
public class GPSService {

    @Autowired
    DevicesApi devicesApi;

    @Autowired
    GPSDeviceMapper gpsMapper;

    @Autowired
    GPSRepository gpsRepository;


    @Autowired
    RouteService routeService;


    public GPSResponseDto createGPS(VehicleEntity vehicle, GPSDeviceRequestDto gpsDeviceRequestDto) throws ServiceException, TrakerException {
        if(gpsRepository.existsByRegisterDeviceId(gpsDeviceRequestDto.getDeviceRegisterId())){
            throw new TrakerException("Ese identificador ya existe", HttpStatus.CONFLICT);
        }
        Device device = new Device();
        device.setUniqueId(gpsDeviceRequestDto.getDeviceRegisterId().toString());
        device.setName(gpsDeviceRequestDto.getName().toString());
        try {
            Mono<Device> response = devicesApi.devicesPost(device);
            GPSEntity gpsEntity = gpsMapper.mapDeviceToGpsEntity(response.block());
            gpsEntity.setName(gpsDeviceRequestDto.getName());
            gpsEntity.setStatus(GPSStatusEnum.ACTIVE);
            gpsEntity.setVehicle(vehicle);
            gpsEntity = gpsRepository.save(gpsEntity);
            return this.enableGPS(gpsEntity.getTraccarDeviceId());
        } catch (WebClientResponseException e) {
            throw new ServiceException("Exception creating device:" + e.getResponseBodyAsString(), e);
        }
    }


    public GPSResponseDto getGPS(Long gpsId) {
        return gpsRepository.findById(gpsId).map(gpsMapper::mapEntityToResponse).orElseThrow(()->new ResponseStatusException(HttpStatus.NOT_FOUND, "Gps device not found - " + gpsId));
    }

    public List<GPSShortResponseDto> getListGPS(Long vehicleId){
        return gpsRepository.findByVehicle_Id(vehicleId).stream().map(gpsMapper::mapEntityToShortResponse).collect(Collectors.toList());
    }


    public void updateGPS(MessageWebSocket message) {
        if(message.getPositions() != null) {
            message.getPositions().forEach(position -> {
                if (position.getAttributes() != null) {
                    gpsRepository.findById(position.getDeviceId()).ifPresent(g -> {
                        if(g.getStatus().equals(GPSStatusEnum.ACTIVE)){
                            g.setActualDistance(position.getAttributes().getDistance());
                            g.setTotalDistance(position.getAttributes().getTotalDistance());
                            g.setMotion(position.getAttributes().getMotion());
                            gpsRepository.save(g);
                            log.info("Gps updated - " + "device:" + position.getDeviceId() + " distance: " + position.getAttributes().getDistance());
                            routeService.updateRoutes(position, g);
                        }
                    });
                }
            });
        }
        if(message.getDevices() != null){
            message.getDevices().stream().forEach(d -> {
                gpsRepository.findById(d.getId()).ifPresent(g -> {
                    if(g.getStatus().equals(GPSStatusEnum.ACTIVE)){
                        g.setLastUpdated(OffsetDateTime.ofInstant(d.getLastUpdate().toInstant(), ZoneId.systemDefault()));
                        gpsRepository.save(g);
                        log.info("Device updated - id:" + d.getId() + " - " + d.getLastUpdate());
                    }
                });
            });
        }

    }

    public void deleteByVehicleId(long id){
        var gpsList = gpsRepository.findByVehicle_Id(id);
        for(var gps: gpsList){
            this.deleteById(gps.getTraccarDeviceId());
        }
    }

    @Transactional
    public void deleteById(Long id){
        routeService.deleteByGpsId(id);
        devicesApi.devicesIdDelete(id.intValue());
        gpsRepository.deleteById(id);
    }


    public GPSResponseDto updateGPSStatus(Long gpsId, GPSStatusEnum status){
        return switch (status){
            case ACTIVE -> this.enableGPS(gpsId);
            case INACTIVE -> this.disableGPS(gpsId);
        };
    }

    private GPSResponseDto enableGPS(Long gpsId){
        var gps = gpsRepository.findById(gpsId).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "GPS device not found - " + gpsId));
        gps.setStatus(GPSStatusEnum.ACTIVE);
        var listGps = gpsRepository.findByVehicle_Id(gps.getVehicle().getId());
        listGps.stream().filter(g -> !g.getTraccarDeviceId().equals(gpsId)).forEach(
                g->{
                    Device device = devicesApi.devicesGet(null,null,g.getTraccarDeviceId().intValue(),null).blockFirst();
                    if(!device.getDisabled() || g.getStatus().equals(GPSStatusEnum.ACTIVE)){
                        device.setDisabled(true);
                        devicesApi.devicesIdPut(g.getTraccarDeviceId().intValue(), device).block();
                        g.setStatus(GPSStatusEnum.INACTIVE);
                        gpsRepository.save(g);
                    }
                }
        );
        Device device = devicesApi.devicesGet(null,null,gpsId.intValue(),null).blockFirst();
        device.disabled(false);
        devicesApi.devicesIdPut(gpsId.intValue(), device).block();
        return gpsMapper.mapEntityToResponse(gpsRepository.save(gps));
    }

    private GPSResponseDto disableGPS(Long gpsId){
        var gps = gpsRepository.findById(gpsId).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "GPS device not found - " + gpsId));
        gps.setStatus(GPSStatusEnum.INACTIVE);
        Device device = devicesApi.devicesGet(null,null,gpsId.intValue(),null).blockFirst();
        device.disabled(true);
        devicesApi.devicesIdPut(gpsId.intValue(), device).block();
        return gpsMapper.mapEntityToResponse(gpsRepository.save(gps));
    }

    public Optional<GPSEntity> getGpsEntity(Long gpsId) {
        return gpsRepository.findById(gpsId);
    }
}
