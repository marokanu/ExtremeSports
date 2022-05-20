package com.sport.admin.repository;

import com.sport.admin.entity.Activity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;

import java.util.List;

public interface ActivityRepository extends PagingAndSortingRepository<Activity, Integer> {

    public Long countById (Integer id);

    public Activity findByName(String name);

    @Query("SELECT a FROM Activity a WHERE a.name LIKE %?1%")
    public Page<Activity> findAll(String keyword, Pageable pageable);

    @Query("SELECT NEW Activity (a.id, a.name) FROM Activity a ORDER BY a.name ASC")
    public List<Activity> findAll();
}
