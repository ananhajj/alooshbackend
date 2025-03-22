package com.dashboard.AlooshDashBoard.api;

import com.dashboard.AlooshDashBoard.Cloudinary.CloudinaryService;
import com.dashboard.AlooshDashBoard.entity.models.Category;
import com.dashboard.AlooshDashBoard.exceptionHandler.CategoryException;
import com.dashboard.AlooshDashBoard.service.CategoryService.CategoryService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Map;
@RestController
@RequestMapping("/api")
public class CategoryApi {
    @Autowired
    private CategoryService categoryService;
    @Autowired
    private CloudinaryService cloudinaryService;

    @PostMapping("/category")
    public ResponseEntity<?> addCategory(@RequestParam Map<String, String> params,
                                         @RequestParam("image") MultipartFile file) {
        try {
            String categoryName = params.get("name");
            String imgUrl=cloudinaryService.uploadCategoryFile(file);

            Category category=categoryService.newCategory(categoryName, imgUrl);

            Map<String,Object>response=Map.of(
                    "message", "Category added successfully",
                    "category", category
            );
            return ResponseEntity.ok(response);
        }catch (Exception ex) {
            Map<String, Object> errorResponse = Map.of(
                    "message", ex.getMessage()
            );


            HttpStatus status = HttpStatus.INTERNAL_SERVER_ERROR;

            if (ex instanceof NullPointerException) {
                status = HttpStatus.BAD_REQUEST;
            } else if (ex instanceof IllegalArgumentException) {
                status = HttpStatus.BAD_REQUEST;
            } else if (ex instanceof CategoryException) {
                status = HttpStatus.NOT_FOUND;
            }
            return ResponseEntity.status(status).body(errorResponse);
        }
    }
    @GetMapping("/categories")
    public ResponseEntity<?> getAllCategories() {
        try {
            List<Category> categories=categoryService.getAllCategories();
            Map<String, Object> response = Map.of(
                    "categories", categories
            );
            return ResponseEntity.ok(response);
        }catch (Exception ex){
            Map<String, Object> errorResponse = Map.of(
                    "message", ex.getMessage()
            );
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(errorResponse);
        }
    }
    @GetMapping("/category/{categoryId}")
    public ResponseEntity<?> getCategory(@PathVariable int categoryId) {
      try {
          Category category=categoryService.getCategoryById(categoryId);
          Map<String, Object> response = Map.of(
                  "category", category
          );
          return ResponseEntity.ok(response);
      }catch (Exception ex){
          Map<String, Object> errorResponse = Map.of(
                  "message", ex.getMessage()
          );
          HttpStatus status = HttpStatus.INTERNAL_SERVER_ERROR;
          if (ex instanceof NullPointerException) {
              status = HttpStatus.BAD_REQUEST;
          }else if (ex instanceof IllegalArgumentException) {
              status = HttpStatus.BAD_REQUEST;
          }else if (ex instanceof CategoryException) {
              status = HttpStatus.NOT_FOUND;
          }
          return ResponseEntity.status(status).body(errorResponse);
      }
    }
    @PutMapping("/category/{categoryId}")
    public ResponseEntity<?>updateCategory(@PathVariable int categoryId,
                                           @RequestPart(value = "categoryName",required = false) String categoryName,
                                           @RequestPart(value = "image",required = false)MultipartFile file) {
        try {
            Category category=categoryService.updateCategory(categoryId,categoryName,file);
            Map<String,Object>response=Map.of(
                    "category", category
            );
            return ResponseEntity.ok(response);
        }catch (Exception ex){
            Map<String, Object> errorResponse = Map.of(
                    "message", ex.getMessage()
            );
            HttpStatus status = HttpStatus.INTERNAL_SERVER_ERROR;
            if (ex instanceof NullPointerException) {
                status = HttpStatus.BAD_REQUEST;
            }else if (ex instanceof IllegalArgumentException) {
                status = HttpStatus.BAD_REQUEST;
            }else if (ex instanceof CategoryException) {
                status = HttpStatus.NOT_FOUND;
            }
            return ResponseEntity.status(status).body(errorResponse);
        }
    }
    @DeleteMapping("/category/{categoryId}")
    public ResponseEntity<?> deleteCategory(@PathVariable int categoryId) {
        try {
            categoryService.deleteCategory(categoryId);
            Map<String,Object>response=Map.of(
                    "status","success",
                    "message","Category & all product deleted successfully"
            );
            return ResponseEntity.ok(response);

        }catch (Exception ex){
            Map<String, Object> errorResponse = Map.of(
                    "message", ex.getMessage()
            );
            HttpStatus status = HttpStatus.INTERNAL_SERVER_ERROR;
            if (ex instanceof NullPointerException) {
                status = HttpStatus.BAD_REQUEST;
            }else if (ex instanceof IllegalArgumentException) {
                status = HttpStatus.BAD_REQUEST;
            }else if (ex instanceof CategoryException) {
                status = HttpStatus.NOT_FOUND;
            }
            return ResponseEntity.status(status).body(errorResponse);
        }
    }
}
