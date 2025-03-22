package com.dashboard.AlooshDashBoard.service.CategoryService;

import com.dashboard.AlooshDashBoard.entity.models.Category;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Map;

public interface CategoryService {
    Category newCategory(String categoryName, String categoryImg);
    List<Category> getAllCategories();
    Category getCategoryById(int id);
    Category updateCategory(int CategoryId, String categoryName, MultipartFile CategoryImg);
    void deleteCategory(int CategoryId);

    int totalCategory();

    List<Map<String, Object>> getSalesPercentageByCategory();
}
