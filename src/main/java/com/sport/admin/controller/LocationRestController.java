package com.sport.admin.controller;

import com.sport.admin.entity.Location;
import com.sport.admin.repository.LocationRepository;
import com.sport.admin.service.LocationService;
import org.springframework.data.repository.query.Param;
import org.springframework.web.bind.annotation.*;

import java.sql.Date;
import java.util.List;


@RestController
public class LocationRestController {

    private final LocationService locationService;

    public LocationRestController(LocationService locationService) {
        this.locationService = locationService;
    }

    @PostMapping("/locations/check_unique")
    public String checkUnique(@Param("id") Integer id,
                              @Param("name") String name) {

        return locationService.checkUnique(id, name);
    }

}
