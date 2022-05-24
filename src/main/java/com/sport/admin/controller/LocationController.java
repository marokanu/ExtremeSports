package com.sport.admin.controller;

import com.sport.admin.entity.Activity;
import com.sport.admin.entity.Category;
import com.sport.admin.entity.Location;
import com.sport.admin.error.LocationNotFoundException;
import com.sport.admin.service.ActivityService;
import com.sport.admin.service.CategoryService;
import com.sport.admin.service.LocationService;
import org.springframework.data.domain.Page;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.sql.Date;
import java.util.List;

@Controller
public class LocationController {

    private final LocationService locationService;
    private final ActivityService activityService;
    private final CategoryService categoryService;

    public LocationController(LocationService locationService, ActivityService activityService, CategoryService categoryService) {
        this.locationService = locationService;
        this.activityService = activityService;
        this.categoryService = categoryService;
    }

    @GetMapping("/locations")
    public String listFirstPage(Model model) {

        return listByPage(1, model, "name", "asc", null, 0);
    }

    @GetMapping("/locations/page/{pageNum}")
    public String listByPage(@PathVariable(name = "pageNum") int pageNum,
                             Model model,
                             @Param("sortField") String sortField,
                             @Param("sortDir") String sortDir,
                             @Param("keyword") String keyword,
                             @Param("categoryId") Integer categoryId) {

        Page<Location> page = locationService.listByPage(pageNum, sortField, sortDir, keyword, categoryId);

        List<Location> listLocations = page.getContent();
        List<Category> listCategories = categoryService.listCategoriesUsedInForm();

        long startCount = (pageNum - 1) * LocationService.LOCATIONS_PER_PAGE + 1;
        long endCount = startCount + LocationService.LOCATIONS_PER_PAGE - 1;
        if (endCount > page.getTotalElements()) {
            endCount = page.getTotalElements();
        }
        String reverseSortDir = sortDir.equals("asc") ? "desc" : "asc";

        if (categoryId != null) model.addAttribute("categoryId", categoryId);

        model.addAttribute("currentPage", pageNum);
        model.addAttribute("totalPages", page.getTotalPages());
        model.addAttribute("startCount", startCount);
        model.addAttribute("endCount", endCount);
        model.addAttribute("totalItems", page.getTotalElements());
        model.addAttribute("sortField", sortField);
        model.addAttribute("sortDir", sortDir);
        model.addAttribute("reverseSortDir", reverseSortDir);
        model.addAttribute("keyword", keyword);
        model.addAttribute("listLocations", listLocations);
        model.addAttribute("listCategories", listCategories);

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

    @GetMapping("/locations/edit/{id}")
    public String editLocation(@PathVariable("id") Integer id,
                              Model model,
                              RedirectAttributes redirectAttributes) {

        try {
            Location location = locationService.get(id);
            List<Activity> listActivities = activityService.listAll();

            model.addAttribute("location", location);
            model.addAttribute("listActivities", listActivities);
            model.addAttribute("pageTitle", "Edit Location (ID: " + id + ")");

            return "locations/location_form";
        } catch (LocationNotFoundException e) {
            redirectAttributes.addFlashAttribute("message", e.getMessage());

            return "redirect:/locations";
        }
    }

    @GetMapping("/locations/detail/{id}")
    public String viewLocationsDetails(@PathVariable("id") Integer id,
                                       Model model,
                                       RedirectAttributes redirectAttributes) {

        try {
            Location location = locationService.get(id);

            model.addAttribute("location", location);

            return "locations/location_detail_modal";
        } catch (LocationNotFoundException e) {
            redirectAttributes.addFlashAttribute("message", e.getMessage());

            return "redirect:/locations";
        }
    }

}