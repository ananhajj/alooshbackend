package com.dashboard.AlooshDashBoard.service.CategoryService;

import com.dashboard.AlooshDashBoard.Cloudinary.CloudinaryService;
import com.dashboard.AlooshDashBoard.entity.models.Category;
import com.dashboard.AlooshDashBoard.exceptionHandler.CategoryException;
import com.dashboard.AlooshDashBoard.repository.CategoryRepository;
import com.dashboard.AlooshDashBoard.repository.InvoiceItemRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class CategoryServiceImp implements CategoryService {
    @Autowired
    private CategoryRepository categoryRepository;
    @Autowired
    private CloudinaryService cloudinaryService;
    @Autowired
    private InvoiceItemRepository invoiceItemRepository;
    @Override
    public Category newCategory(String categoryName, String categoryImg) {

        if(categoryRepository.existsCategoryByCategoryName( categoryName )){
            throw new CategoryException(HttpStatus.CONFLICT, "Category already exists");
        }
         Category category = new Category();
        category.setCategoryName(categoryName);
        category.setCategoryImg(categoryImg);

        return categoryRepository.save(category);
    }

    @Override
    public List<Category> getAllCategories() {
        return categoryRepository.findAll();
    }

    @Override
    public Category getCategoryById(int id) {
        Category category=categoryRepository.findById(id).orElseThrow(
                ()-> new CategoryException(HttpStatus.NOT_FOUND, "Category not found")
        );
        return category;
    }

    @Override
    public Category updateCategory(int CategoryId, String categoryName, MultipartFile CategoryImg) {
        Category category=categoryRepository.findById(CategoryId).orElseThrow(
                ()-> new CategoryException(HttpStatus.NOT_FOUND, "Category not found")
        );

        if(categoryName!=null){
            category.setCategoryName(categoryName);
        }
        if(CategoryImg!=null&&!CategoryImg.isEmpty()){
            try {
                String image = cloudinaryService.uploadCategoryFile(CategoryImg);
                category.setCategoryImg(image);
            }catch (Exception e){
                throw new CategoryException(HttpStatus.CONFLICT, "Category image not found");
            }
            }
        return categoryRepository.save(category);
    }

    @Override
    public void deleteCategory(int CategoryId) {
        Category category=categoryRepository.findById(CategoryId).orElseThrow(
                ()-> new CategoryException(HttpStatus.NOT_FOUND, "Category not found")
        );
        categoryRepository.delete(category);
    }

    @Override
    public int totalCategory() {
        return categoryRepository.countCategories();
    }

    @Override
    public List<Map<String, Object>> getSalesPercentageByCategory() {
        List<Object[]> results = invoiceItemRepository.findSalesByCategory();
        List<Map<String, Object>> categorySalesList = new ArrayList<>();


        int totalSales = results.stream().mapToInt(row -> ((Number) row[3]).intValue()).sum();

        for (Object[] row : results) {
            Map<String, Object> categoryData = new HashMap<>();
            categoryData.put("id", row[0]);
            categoryData.put("name", row[1]);
            categoryData.put("imageUrl", row[2]);

            int categorySales = ((Number) row[3]).intValue();
            categoryData.put("sales", categorySales);


            double percentage = totalSales == 0 ? 0 : (categorySales * 100.0) / totalSales;
            categoryData.put("percentage", Math.round(percentage * 100.0) / 100.0);

            categorySalesList.add(categoryData);
        }

        return categorySalesList;
    }
}
