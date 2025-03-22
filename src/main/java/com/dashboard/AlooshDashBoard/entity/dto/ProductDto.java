package com.dashboard.AlooshDashBoard.entity.dto;


import com.dashboard.AlooshDashBoard.entity.models.Product;
import org.springframework.web.multipart.MultipartFile;

public class ProductDto {
    private int productId;
    private String name;
    private Double price;
    private Double quantity;
    private String unitType;
    private String imageUrl;
    private int categoryId;
    private double rollSize;
    private double quantityInMeters ;
    private double pricePerMeters;

    public ProductDto() {}
    public ProductDto(Product product) {
        this.productId = product.getProductId();
        this.name=product.getProductName();
        this.price = product.getPrice();
        this.quantity = product.getQuantity();
        this.unitType=product.getUnitType().toString();
        this.imageUrl=product.getProductImg();
        this.categoryId=product.getCategory().getCategoryId();
        this.rollSize=product.getRollSize();
        this.quantityInMeters=product.getQuantityInMeters();
        this.pricePerMeters=product.getPricePerMeters();
    }
    public int getProductId() {
        return productId;
    }

    public void setProductId(int productId) {
        this.productId = productId;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    public String getUnitType() {
        return unitType;
    }

    public void setUnitType(String unitType) {
        this.unitType = unitType;
    }

    public int getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(int categoryId) {
        this.categoryId = categoryId;
    }

    public Double getQuantity() {
        return quantity;
    }

    public void setQuantity(Double quantity) {
        this.quantity = quantity;
    }

    public double getRollSize() {
        return rollSize;
    }

    public void setRollSize(double rollSize) {
        this.rollSize = rollSize;
    }

    public double getQuantityInMeters() {
        return quantityInMeters;
    }

    public void setQuantityInMeters(double quantityInMeters) {
        this.quantityInMeters = quantityInMeters;
    }

    public double getPricePerMeters() {
        return pricePerMeters;
    }

    public void setPricePerMeters(double pricePerMeters) {
        this.pricePerMeters = pricePerMeters;
    }
}
