package com.ricardo.traker.service.impl;

import com.ricardo.traker.exception.ServiceException;
import com.ricardo.traker.mapper.GPSMapper;
import com.ricardo.traker.model.dto.request.VehicleRequestDto;
import com.ricardo.traker.model.entity.GPSEntity;
import com.ricardo.traker.repository.GPSRepository;
import com.ricardo.traker.service.GPSService;
import com.ricardo.traker.traccar.Device;
import com.ricardo.traker.traccar.api.DevicesApi;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClientResponseException;
import reactor.core.publisher.Mono;

import java.util.Optional;

@Service
@Slf4j
public class GPSServiceImpl implements GPSService {

    @Autowired
    DevicesApi devicesApi;

    @Autowired
    GPSMapper gpsMapper;

    @Autowired
    GPSRepository gpsRepository;

    @Override
    public GPSEntity createGPS(VehicleRequestDto vehicleRequestDto) throws ServiceException {
        Device device = new Device();
        device.setUniqueId(vehicleRequestDto.getDeviceRegisterId().toString());
        device.setName(vehicleRequestDto.getLicense());
        try {
            Mono<Device> response = devicesApi.devicesPost(device);
            GPSEntity gpsEntity = gpsMapper.mapDeviceToGpsEntity(response.block());
            return gpsRepository.save(gpsEntity);
        } catch (WebClientResponseException e) {
            throw new ServiceException("Exception creating device:" + e.getResponseBodyAsString(), e);

        }
    }

    @Override
    public Optional<GPSEntity> getGPSEntity(Integer gpsId) {
        return gpsRepository.findById(gpsId);
    }
}
