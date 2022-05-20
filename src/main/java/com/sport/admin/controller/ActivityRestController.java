package com.sport.admin.controller;

import com.sport.admin.service.ActivityService;
import org.springframework.data.repository.query.Param;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ActivityRestController {

    private final ActivityService activityService;

    public ActivityRestController(ActivityService activityService) {
        this.activityService = activityService;
    }

    @PostMapping("/activities/check_unique")
    public String checkUnique(@Param("id") Integer id,
                              @Param("name") String name) {

        return activityService.checkUnique(id, name);
    }
}
