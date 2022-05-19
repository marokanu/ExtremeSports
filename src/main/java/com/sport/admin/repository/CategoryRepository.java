package com.sport.admin.repository;

import com.sport.admin.entity.Category;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;

import java.util.List;

public interface CategoryRepository extends PagingAndSortingRepository<Category, Integer> {

    @Query("SELECT c FROM Category c WHERE c.parent.id is NULL")
    public List<Category> findRootCategories(Sort sort);

    @Query("SELECT c FROM Category c WHERE c.parent.id is NULL")
    public Page<Category> findRootCategories(Pageable pageable);

    public Category findByName(String name);

    public Category findByAlias(String alias);

    public Long countById(Integer id);
}
