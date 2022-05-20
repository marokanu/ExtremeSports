package com.sport.admin.service;

import com.sport.admin.entity.Activity;
import com.sport.admin.error.ActivityNotFoundException;
import com.sport.admin.repository.ActivityRepository;
import com.sport.admin.service.impl.IActivityService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.NoSuchElementException;

@Service
public class ActivityService implements IActivityService {

    public static final int ACTIVITIES_PER_PAGE = 5;

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

    @Override
    public String checkUnique(Integer id, String name) {
        boolean isCreatingNew = (id == null || id == 0);

        Activity activityByName = activityRepository.findByName(name);

        if (isCreatingNew) {
            if (activityByName != null) return "Duplicate";
        } else {
            if (activityByName != null && activityByName.getId() != id) {
                return "Duplicate";
            }
        }
        return "OK";
    }

    @Override
    public Page<Activity> listByPage(int pageNum, String sortField, String sortDir, String keyword) {
        Sort sort = Sort.by(sortField);

        sort = sortDir.equals("asc") ? sort.ascending() : sort.descending();

        Pageable pageable = PageRequest.of(pageNum-1, ACTIVITIES_PER_PAGE, sort);

        if (keyword != null) {
            return activityRepository.findAll(keyword, pageable);
        }
        return activityRepository.findAll(pageable);
    }
}
