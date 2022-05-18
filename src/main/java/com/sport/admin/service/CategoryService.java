package com.sport.admin.service;

import com.sport.admin.entity.Category;
import com.sport.admin.repository.CategoryRepository;
import com.sport.admin.service.impl.ICategoryService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CategoryService implements ICategoryService {

    private final CategoryRepository categoryRepository;

    public CategoryService(CategoryRepository categoryRepository) {
        this.categoryRepository = categoryRepository;
    }


    @Override
    public List<Category> listAll() {
        return (List<Category>) categoryRepository.findAll();
    }
}
