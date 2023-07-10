package com.ricardo.traker.service.impl;

import com.ricardo.traker.exception.ServiceException;
import com.ricardo.traker.mapper.GPSMapper;
import com.ricardo.traker.model.dto.MessageWebSocket;
import com.ricardo.traker.model.dto.PositionsWebSocket;
import com.ricardo.traker.model.dto.request.VehicleRequestDto;
import com.ricardo.traker.model.entity.GPSEntity;
import com.ricardo.traker.repository.GPSRepository;
import com.ricardo.traker.service.GPSService;
import com.ricardo.traker.traccar.Device;
import com.ricardo.traker.traccar.api.DevicesApi;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClientResponseException;
import reactor.core.publisher.Mono;

import java.time.LocalDateTime;
import java.time.ZoneId;
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

    @Override
    public void updateGPSByWebSocket(MessageWebSocket message) {
        if(message.getDevices() != null){
            message.getDevices().stream().forEach(d -> {
                gpsRepository.findById(d.getId()).ifPresent(g -> {
                    g.setLastUpdated(LocalDateTime.ofInstant(d.getLastUpdate().toInstant(), ZoneId.systemDefault()));
                    g.setStatus(d.getStatus());
                    gpsRepository.save(g);
                    log.info("device updated");
                });
            });
        }
    }

    @Override
    public void updateGPSByPosition(PositionsWebSocket position) {
        if(position.getAttributes() != null){
            gpsRepository.findById(position.getDeviceId()).ifPresent(g -> {
                g.setActualDistance(position.getAttributes().getDistance());
                g.setTotalDistance(position.getAttributes().getTotalDistance());
                g.setMotion(position.getAttributes().getMotion());
                gpsRepository.save(g);
                log.info("gps updated");
            });
        }
    }
}
