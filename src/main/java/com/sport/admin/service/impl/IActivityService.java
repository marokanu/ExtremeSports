package com.sport.admin.service.impl;

import com.sport.admin.entity.Activity;
import com.sport.admin.error.ActivityNotFoundException;

import javax.persistence.criteria.CriteriaBuilder;
import java.util.List;

public interface IActivityService {

    public List<Activity> listAll();

    public Activity save(Activity activity);

    public Activity get(Integer id) throws ActivityNotFoundException;

    public void delete(Integer id) throws ActivityNotFoundException;

    public String checkUnique(Integer id, String name);
}
