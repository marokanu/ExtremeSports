package com.sport.admin.service;

import com.sport.admin.entity.Location;
import com.sport.admin.error.LocationNotFoundException;
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
        return locationRepository.save(location);
    }

    @Override
    public String checkUnique(Integer id, String name) {

        boolean isCreatingNew = (id == null || id == 0);
        Location locationByName = locationRepository.findByName(name);

        if (isCreatingNew) {
            if (locationByName != null) return "Duplicate";
        } else {
            if (locationByName != null && locationByName.getId() != id) {
                return "Duplicate";
            }
        }
        return "OK";
    }

    @Override
    public void delete(Integer id) throws LocationNotFoundException {
        Long countById = locationRepository.countById(id);

        if (countById == null || countById == 0) {
            throw new LocationNotFoundException("Could not find any product with ID " + id);
        }

        locationRepository.deleteById(id);
    }
}
