package com.sport.admin.repository;

import com.sport.admin.entity.Location;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;


import java.sql.Date;

@Repository
public interface LocationRepository extends PagingAndSortingRepository<Location, Integer> {

    public Location findByName(String name);

    public Long countById(Integer id);

    @Query("SELECT l FROM Location l WHERE l.name LIKE %?1%"
          + "OR l.Description LIKE %?1% "
          + "OR l.activity.name LIKE %?1% "
          + "OR l.category.name LIKE %?1%")
    public Page<Location> findAll(String keyword , Pageable pageable);

    @Query("SELECT p FROM Location p WHERE p.category.id = ?1 "
            + "OR p.category.allParentIDs LIKE %?2%")
    public Page<Location> findAllInCategory(Integer categoryId, String categoryIdMatch,
                                           Pageable pageable);

    @Query("SELECT p FROM Location p WHERE (p.category.id = ?1 "
            + "OR p.category.allParentIDs LIKE %?2%)"
            + "AND (p.name LIKE %?3% "
            + "OR p.Description LIKE %?3% "
            + "OR p.activity.name LIKE %?3% "
            + "OR p.category.name LIKE %?3%)")
    public Page<Location> searchInCategory(Integer categoryId, String categoryIdMatch,
                                          String keyword, Pageable pageable);

    @Query("SELECT l FROM Location l WHERE l.startAt BETWEEN  ?1 AND ?2"
            + " AND l.endAt BETWEEN ?1 AND ?2")
    Page<Location> findByDateInAndDateOut(Date DateIn, Date DateOut, Pageable pageable);

}
