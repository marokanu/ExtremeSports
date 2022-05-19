package com.sport.admin.service.impl;

import com.sport.admin.entity.Category;
import com.sport.admin.error.CategoryNotFoundException;

import java.util.List;

public interface ICategoryService {

    public List<Category> listAll(String sortDir);

    public List<Category> listCategoriesUsedInForm();

    public Category save(Category category);

    public Category get(Integer id) throws CategoryNotFoundException;

    public String checkUnique(Integer id, String name, String alias);

    public void delete(Integer id) throws CategoryNotFoundException;
}
