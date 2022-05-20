package com.sport.admin.controller;

import com.sport.admin.entity.Activity;
import com.sport.admin.entity.Category;
import com.sport.admin.error.ActivityNotFoundException;
import com.sport.admin.service.ActivityService;
import com.sport.admin.service.CategoryService;
import com.sport.admin.util.FileUploadUtil;
import org.springframework.data.domain.Page;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.io.IOException;
import java.util.List;

@Controller
public class ActivityController {

    private final ActivityService activityService;
    private final CategoryService categoryService;

    public ActivityController(ActivityService activityService, CategoryService categoryService) {
        this.activityService = activityService;
        this.categoryService = categoryService;
    }

    @GetMapping("/activities")
    public String listFirstPage(Model model) {

        return listByPage(1, model,"name", "asc", null);
    }

    @GetMapping("/activities/page/{pageNum}")
    public String listByPage(@PathVariable(name = "pageNum") int pageNum,
                             Model model,
                             @Param("sortField") String sortField,
                             @Param("sortDir") String sortDir,
                             @Param("keyword") String keyword) {

        Page<Activity> page = activityService.listByPage(pageNum, sortField, sortDir, keyword);
        List<Activity> listActivities = page.getContent();

        long startCount = (pageNum - 1) * ActivityService.ACTIVITIES_PER_PAGE + 1;
        long endCount = startCount + ActivityService.ACTIVITIES_PER_PAGE - 1;
        if (endCount > page.getTotalElements()) {
            endCount = page.getTotalElements();
        }

        String reverseSortDir = sortDir.equals("asc") ? "desc" : "asc";

        model.addAttribute("currentPage", pageNum);
        model.addAttribute("totalPages", page.getTotalPages());
        model.addAttribute("startCount", startCount);
        model.addAttribute("endCount", endCount);
        model.addAttribute("totalItems", page.getTotalElements());
        model.addAttribute("sortField", sortField);
        model.addAttribute("sortDir", sortDir);
        model.addAttribute("reverseSortDir", reverseSortDir);
        model.addAttribute("keyword", keyword);
        model.addAttribute("listActivities", listActivities);

        return "activities/activities";
    }

    @GetMapping("/activities/new")
    public String newActivity(Model model) {
        List<Category> listCategories = categoryService.listCategoriesUsedInForm();

        model.addAttribute("listCategories", listCategories);
        model.addAttribute("activity", new Activity());
        model.addAttribute("pageTitle", "Create New Activity");

        return "activities/activity_form";
    }

    @PostMapping("/activities/save")
    public String saveActivity(Activity activity,
                               @RequestParam("fileImage")MultipartFile multipartFile,
                               RedirectAttributes redirectAttributes) throws IOException {
        if (!multipartFile.isEmpty()) {
            String fileName = StringUtils.cleanPath(multipartFile.getOriginalFilename());

            activity.setLogo(fileName);

            Activity saveActivity = activityService.save(activity);

            String uploadDir = "../activity-logos/" + saveActivity.getId();

            FileUploadUtil.cleanDir(uploadDir);
            FileUploadUtil.saveFile(uploadDir, fileName, multipartFile);
        } else {
            activityService.save(activity);
        }
        redirectAttributes.addFlashAttribute("message", "The activity has been saved!");

        return "redirect:/activities";
    }

    @GetMapping("/activities/delete/{id}")
    public String deleteActivity(@PathVariable(name = "id") Integer id,
                                 Model model,
                                 RedirectAttributes redirectAttributes) {

        try {
            activityService.delete(id);

            String activityDir = "../activity-logos/" + id;
            FileUploadUtil.removeDir(activityDir);

            redirectAttributes.addFlashAttribute("message", "The activity ID " + id + " has been deleted!");
        } catch (ActivityNotFoundException ex) {
            redirectAttributes.addFlashAttribute("message", ex.getMessage());
        }
        return  "redirect:/activities";
    }

    @GetMapping("/activities/edit/{id}")
    public String editBrand(@PathVariable(name = "id") Integer id,
                            Model model,
                            RedirectAttributes ra) {
        try {
            Activity activity = activityService.get(id);
            List<Category> listCategories = categoryService.listCategoriesUsedInForm();

            model.addAttribute("activity", activity);
            model.addAttribute("listCategories", listCategories);
            model.addAttribute("pageTitle", "Edit Activity (ID: " + id + ")");

            return "activities/activity_form";
        } catch (ActivityNotFoundException ex) {
            ra.addFlashAttribute("message", ex.getMessage());
            return "redirect:/activities";
        }
    }
}
