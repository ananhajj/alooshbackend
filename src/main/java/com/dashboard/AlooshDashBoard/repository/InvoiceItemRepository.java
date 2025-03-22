package com.dashboard.AlooshDashBoard.repository;

import com.dashboard.AlooshDashBoard.entity.models.InvoiceItem;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface InvoiceItemRepository extends JpaRepository<InvoiceItem, Integer> {

        @Query("SELECT ii.product, COALESCE(SUM(ii.quantity), 0) AS totalQuantity " +
                "FROM InvoiceItem ii " +
                "GROUP BY ii.product " +
                "ORDER BY totalQuantity DESC")
        List<Object[]> findTopSellingProducts(Pageable pageable);

        @Query("SELECT i.product.productId, i.product.productName, i.product.category.categoryName, " +
                "i.product.price, i.product.productImg, COALESCE(SUM(i.quantity), 0) as totalSales " +
                "FROM InvoiceItem i " +
                "GROUP BY i.product.productId, i.product.productName, i.product.category.categoryName, " +
                "i.product.price, i.product.productImg " +
                "ORDER BY totalSales DESC " +
                "LIMIT 6")
        List<Object[]> findTopSellingProducts();

        @Query("""
    SELECT c.categoryId, c.categoryName, c.categoryImg, COUNT(ii.itemId) AS totalSales
    FROM InvoiceItem ii
    JOIN ii.product p
    JOIN p.category c
    GROUP BY c.categoryId, c.categoryName, c.categoryImg
""")
        List<Object[]> findSalesByCategory();



}
