package com.dashboard.AlooshDashBoard.entity.dto;

import com.dashboard.AlooshDashBoard.entity.models.Product;

import java.util.List;
import java.util.Map;

public class DashBoardInfoDTO {
    double totalSales;
    double unPaidPayment;
    double totalTodayPayment;
    int totalProduct ;
    int totalCategory;
    int countInvoice;
    private List<Map<String, Object>> topSalesProducts;
    List<Map<String, Object>> categorySales ;

    public List<Map<String, Object>> getCategorySales() {
        return categorySales;
    }

    public void setCategorySales(List<Map<String, Object>> categorySales) {
        this.categorySales = categorySales;
    }

    public List<Map<String, Object>> getTopSalesProducts() {
        return topSalesProducts;
    }

    public void setTopSalesProducts(List<Map<String, Object>> topSalesProducts) {
        this.topSalesProducts = topSalesProducts;
    }

    public double getTotalSales() {
        return totalSales;
    }

    public void setTotalSales(double totalSales) {
        this.totalSales = totalSales;
    }

    public double getUnPaidPayment() {
        return unPaidPayment;
    }

    public void setUnPaidPayment(double unPaidPayment) {
        this.unPaidPayment = unPaidPayment;
    }
    public double getTotalTodayPayment() {
        return totalTodayPayment;
    }
    public void setTotalTodayPayment(double totalTodayPayment) {
        this.totalTodayPayment = totalTodayPayment;
    }

    public int getTotalProduct() {
        return totalProduct;
    }

    public void setTotalProduct(int totalProduct) {
        this.totalProduct = totalProduct;
    }
    public int getTotalCategory() {
        return totalCategory;
    }
    public void setTotalCategory(int totalCategory) {
        this.totalCategory = totalCategory;
    }
    public int getCountInvoice() {
        return countInvoice;
    }
    public void setCountInvoice(int countInvoice) {
        this.countInvoice = countInvoice;
    }
}
