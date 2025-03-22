package com.dashboard.AlooshDashBoard.repository;

import com.dashboard.AlooshDashBoard.entity.models.Category;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface CategoryRepository extends JpaRepository<Category, Integer> {
    boolean existsCategoryByCategoryName(String categoryName);
    @Query("SELECT COUNT(c) FROM Category c")
    int countCategories();
}
