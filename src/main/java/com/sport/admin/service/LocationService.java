package com.sport.admin.service;

import com.sport.admin.entity.Location;
import com.sport.admin.error.LocationNotFoundException;
import com.sport.admin.repository.LocationRepository;
import com.sport.admin.service.impl.ILocationService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.sql.Date;
import java.util.List;
import java.util.NoSuchElementException;

@Service
public class LocationService implements ILocationService {

    public static final int LOCATIONS_PER_PAGE = 3;

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

    @Override
    public Location get(Integer id) throws LocationNotFoundException {
        try {
            return locationRepository.findById(id).get();
        } catch (NoSuchElementException ex) {
            throw new LocationNotFoundException("Could not find any product with ID " + id);
        }
    }

    @Override
    public Page<Location> listByPage(int pageNum, String sortField, String sortDir, String keyword, Integer categoryId) {
        Sort sort = Sort.by(sortField);

        sort = sortDir.equals("asc") ? sort.ascending() : sort.descending();

        Pageable pageable = PageRequest.of(pageNum-1, LOCATIONS_PER_PAGE, sort);

        if (keyword != null && !keyword.isEmpty()) {
            if (categoryId != null && categoryId > 0) {
                String categoryIdMatch = "-" + String.valueOf(categoryId) + "-";

                return locationRepository.searchInCategory(categoryId, categoryIdMatch, keyword, pageable);
            }
            return locationRepository.findAll(keyword, pageable);
        }
        if (categoryId != null && categoryId > 0) {

            String categoryIdMatch = "-" + String.valueOf(categoryId) + "-";

            return locationRepository.findAllInCategory(categoryId, categoryIdMatch, pageable);
        }
        return locationRepository.findAll(pageable);
        }

}
