package com.sport.admin.service;

import com.sport.admin.entity.Location;
import com.sport.admin.repository.LocationRepository;
import com.sport.admin.service.impl.ILocationService;
import org.springframework.stereotype.Service;

import java.util.Date;
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

    @Override
    public Location save(Location location) {
        if (location.getId() == null) {
            location.setCreatedTime(new Date());
        }
        location.setUpdatedTime(new Date());

        return locationRepository.save(location);
    }
}
