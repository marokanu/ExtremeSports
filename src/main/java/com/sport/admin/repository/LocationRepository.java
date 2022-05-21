package com.sport.admin.repository;

import com.sport.admin.entity.Location;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;

public interface LocationRepository extends PagingAndSortingRepository<Location, Integer> {

    public Location findByName(String name);

    public Long countById(Integer id);

    @Query("SELECT l FROM Location l WHERE l.name LIKE %?1%"
          + "OR l.Description LIKE %?1% "
          + "OR l.activity.name LIKE %?1% "
          + "OR l.category.name LIKE %?1%")
    public Page<Location> findAll(String keyword , Pageable pageable);
}
