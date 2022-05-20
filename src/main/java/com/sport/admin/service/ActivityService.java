package com.sport.admin.service;

import com.sport.admin.entity.Activity;
import com.sport.admin.error.ActivityNotFoundException;
import com.sport.admin.repository.ActivityRepository;
import com.sport.admin.service.impl.IActivityService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.NoSuchElementException;

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

    @Override
    public Activity save(Activity activity) {
        return activityRepository.save(activity);
    }

    @Override
    public Activity get(Integer id) throws ActivityNotFoundException {
        try {
            return activityRepository.findById(id).get();
        } catch (NoSuchElementException ex) {
            throw new ActivityNotFoundException("Could not find any activity with ID " + id);
        }
    }

    @Override
    public void delete(Integer id) throws ActivityNotFoundException {

        Long countById = activityRepository.countById(id);

        if (countById == null || countById == 0) {
            throw new ActivityNotFoundException("Could not find any activity with ID " + id);
        }
        activityRepository.deleteById(id);
    }
}
