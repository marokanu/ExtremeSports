package com.sport.admin.controller;

import com.sport.admin.entity.Location;
import com.sport.admin.repository.LocationRepository;
import com.sport.admin.service.LocationService;
import org.springframework.data.repository.query.Param;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Date;
import java.util.List;


@RestController
public class LocationRestController {

    private final LocationService locationService;
    private final LocationRepository locationRepository;

    public LocationRestController(LocationService locationService, LocationRepository locationRepository, LocationRepository locationRepository1, LocationRepository locationRepository2) {
        this.locationService = locationService;
        this.locationRepository = locationRepository2;
    }

    @PostMapping("/locations/check_unique")
    public String checkUnique(@Param("id") Integer id,
                              @Param("name") String name) {

        return locationService.checkUnique(id, name);
    }

    @GetMapping("/locations/interval")
    public ResponseEntity<List<Location>> getLocationsByInterval(@RequestParam("startAt") Date startAt,
                                                                 @RequestParam("endAt") Date endAt) {

        return new ResponseEntity<List<Location>>(locationRepository.findByStartAtAndEndAtBetween(startAt, endAt), HttpStatus.OK);
    }

}
