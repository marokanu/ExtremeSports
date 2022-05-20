package com.sport.admin.controller;

import com.sport.admin.entity.Activity;
import com.sport.admin.entity.Category;
import com.sport.admin.service.ActivityService;
import com.sport.admin.service.CategoryService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

@Controller
public class ActivityController {

    private final ActivityService activityService;
    private final CategoryService categoryService;

    public ActivityController(ActivityService activityService, CategoryService categoryService) {
        this.activityService = activityService;
        this.categoryService = categoryService;
    }

    @GetMapping("/activities")
    public String listAll(Model model) {

        List<Activity> listActivities = activityService.listAll();

        model.addAttribute("listActivities", listActivities);

        return "activities/activities";
    }

    @GetMapping("/activities/new")
    public String newActivity(Model model) {
        List<Category> listCategories = categoryService.listCategoriesUsedInForm();

        model.addAttribute("listCategories", listCategories);
        model.addAttribute("activity", new Activity());
        model.addAttribute("pageTitle", "Create New Activity");

        return "activities/activity_form";
    }
}
