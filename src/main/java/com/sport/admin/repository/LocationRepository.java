package com.sport.admin.repository;

import com.sport.admin.entity.Location;
import org.springframework.data.repository.PagingAndSortingRepository;

public interface LocationRepository extends PagingAndSortingRepository<Location, Integer> {

    public Location findByName(String name);
}
