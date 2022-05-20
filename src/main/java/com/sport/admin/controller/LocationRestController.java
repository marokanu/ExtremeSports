package com.sport.admin.controller;

import com.sport.admin.service.LocationService;
import org.springframework.data.repository.query.Param;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

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
