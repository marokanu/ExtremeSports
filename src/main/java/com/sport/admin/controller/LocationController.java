package com.sport.admin.controller;

import com.sport.admin.entity.Activity;
import com.sport.admin.entity.Location;
import com.sport.admin.error.LocationNotFoundException;
import com.sport.admin.service.ActivityService;
import com.sport.admin.service.LocationService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;

@Controller
public class LocationController {

    private final LocationService locationService;
    private final ActivityService activityService;

    public LocationController(LocationService locationService, ActivityService activityService) {
        this.locationService = locationService;
        this.activityService = activityService;
    }

    @GetMapping("/locations")
    public String listAll(Model model) {
        List<Location> listLocations = locationService.listAll();

        model.addAttribute("listLocations", listLocations);

        return "locations/locations";
    }

    @GetMapping("/locations/new")
    public String newLocation(Model model) {
        List<Activity> listActivities = activityService.listAll();

        Location location = new Location();

        model.addAttribute("location", location);
        model.addAttribute("listActivities", listActivities);
        model.addAttribute("pageTitle", "Create New Location");

        return "locations/location_form";
    }

    @PostMapping("/locations/save")
    public String saveLocation(Location location,
                               RedirectAttributes redirectAttributes) {

        locationService.save(location);

        redirectAttributes.addFlashAttribute("message", "The Location has been saved!");

        return "redirect:/locations";
    }

    @GetMapping("/locations/delete/{id}")
    public String deleteLocation(@PathVariable(name = "id") Integer id,
                                 Model model,
                                 RedirectAttributes redirectAttributes) {
        try {
            locationService.delete(id);

            redirectAttributes.addFlashAttribute("message",
                    "The Location ID " + id + " has been deleted successfully");
        } catch (LocationNotFoundException ex) {
            redirectAttributes.addFlashAttribute("message", ex.getMessage());
        }
        return "redirect:/locations";
    }
}