package com.sport.admin.controller;

import com.sport.admin.entity.Location;
import com.sport.admin.service.LocationService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

@Controller
public class LocationController {

    private final LocationService locationService;

    public LocationController(LocationService locationService) {
        this.locationService = locationService;
    }

    @GetMapping("/locations")
    public String listAll (Model model) {
        List<Location> listLocations = locationService.listAll();

        model.addAttribute("listLocations", listLocations);

        return "locations/locations";
    }
}
