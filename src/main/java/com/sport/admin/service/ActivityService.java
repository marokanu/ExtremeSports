package com.sport.admin.service;

import com.sport.admin.entity.Activity;
import com.sport.admin.repository.ActivityRepository;
import com.sport.admin.service.impl.IActivityService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ActivityService implements IActivityService {

    private final ActivityRepository activityRepository;

    public ActivityService(ActivityRepository activityRepository) {
        this.activityRepository = activityRepository;
    }

    @Override
    public List<Activity> listAll() {
        return (List<Activity>) activityRepository.findAll();
    }
}
