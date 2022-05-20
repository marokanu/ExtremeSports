package com.sport.admin.controller;

import com.sport.admin.entity.Activity;
import com.sport.admin.entity.Category;
import com.sport.admin.entity.CategoryDTO;
import com.sport.admin.error.ActivityNotFoundException;
import com.sport.admin.error.ActivityNotFoundRestException;
import com.sport.admin.service.ActivityService;
import org.springframework.data.repository.query.Param;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

@RestController
public class ActivityRestController {

    private final ActivityService activityService;

    public ActivityRestController(ActivityService activityService) {
        this.activityService = activityService;
    }

    @PostMapping("/activities/check_unique")
    public String checkUnique(@Param("id") Integer id,
                              @Param("name") String name) {

        return activityService.checkUnique(id, name);
    }

    @GetMapping("/activities/{id}/categories")
    public List<CategoryDTO> listCategoriesByActivities(@PathVariable(name = "id") Integer activityId)
        throws ActivityNotFoundRestException {

        List<CategoryDTO> listCategories = new ArrayList<>();

        try {
            Activity activity = activityService.get(activityId);
            Set<Category> categories = activity.getCategories();

            for (Category category : categories) {
                CategoryDTO dto = new CategoryDTO(category.getId(), category.getName());

                listCategories.add(dto);
            }
            return listCategories;
        } catch (ActivityNotFoundException e) {
            throw new ActivityNotFoundRestException();
        }
    }
}
