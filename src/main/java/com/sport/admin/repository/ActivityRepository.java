package com.sport.admin.repository;

import com.sport.admin.entity.Activity;
import org.springframework.data.repository.PagingAndSortingRepository;

public interface ActivityRepository extends PagingAndSortingRepository<Activity, Integer> {

    public Long countById (Integer id);

    public Activity findByName(String name);
}
