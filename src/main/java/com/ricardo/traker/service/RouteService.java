package com.ricardo.traker.service;

import com.ricardo.traker.model.dto.MessageWebSocket;
import com.ricardo.traker.model.entity.GPSEntity;
import com.ricardo.traker.repository.RouteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class RouteService {

    @Autowired
    RouteRepository routeRepository;

    @Autowired
    PositionService positionService;

    public void updateRoutes(MessageWebSocket message, GPSEntity gps){
        positionService.updatePositions(message, gps);
    }

    public void deleteByGpsId(long id){
        var routes = routeRepository.findByGps_TraccarDeviceId(id);
        for(var r: routes){
            this.deletedById(r.getId());
        }
    }

    public void deletedById(long id){
        positionService.deletePositionsByRoute(id);
        routeRepository.deleteById(id);
    }
}
