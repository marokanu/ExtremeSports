package com.sport.admin.service;

import com.sport.admin.entity.Location;
import com.sport.admin.repository.LocationRepository;
import com.sport.admin.service.impl.ILocationService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class LocationService implements ILocationService {

    private final LocationRepository locationRepository;

    public LocationService(LocationRepository locationRepository) {
        this.locationRepository = locationRepository;
    }

    @Override
    public List<Location> listAll() {
        return (List<Location>) locationRepository.findAll();
    }
}
