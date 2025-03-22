package com.dashboard.AlooshDashBoard.repository;

import com.dashboard.AlooshDashBoard.entity.models.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProductRepository  extends JpaRepository<Product, Integer> {
    boolean existsProductByProductName(String productName);
    @Query("SELECT COALESCE(SUM(p.quantity), 0) FROM Product p")
    Double calculateTotalProductQuantity();



}
