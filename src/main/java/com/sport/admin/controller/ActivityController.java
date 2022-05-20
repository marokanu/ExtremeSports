package com.sport.admin.controller;

import com.sport.admin.entity.Activity;
import com.sport.admin.service.ActivityService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

@Controller
public class ActivityController {

    private final ActivityService activityService;

    public ActivityController(ActivityService activityService) {
        this.activityService = activityService;
    }

    @GetMapping("/activities")
    public String listAll(Model model) {

        List<Activity> listActivities = activityService.listAll();

        model.addAttribute("listActivities", listActivities);

        return "activities/activities";
    }
}
