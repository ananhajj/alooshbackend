package com.dashboard.AlooshDashBoard.entity.models;


import com.dashboard.AlooshDashBoard.entity.enums.UnitType;
import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.sql.Timestamp;
import java.util.Date;

@Entity
@Table(name = "product")
@Getter
@Setter
@NoArgsConstructor
public class Product {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "product_id")
    private int productId;

    @Column(name = "product_name",nullable = false)
    private String productName;

    @Column(name = "price",nullable = false)
    private double price;

    @Enumerated(EnumType.STRING)
    @Column(name = "unit_type",nullable = false)
    private UnitType unitType;

    @Column(name = "product_img")
    private String productImg;

    @Column(name = "quantity", nullable = false)
    private double quantity;



    @Column(name="roll_size",nullable = false)
    private double rollSize;

    @Column(name = "quantity_in_meters",nullable = false)
    private double quantityInMeters;

    @Column(name = "price_per_meters",nullable = false)
    private double pricePerMeters;

    @CreationTimestamp
    @Column(name = "created_at", nullable = false, updatable = false)
    @Temporal(TemporalType.TIMESTAMP)
    private Date createdAt;

    @UpdateTimestamp
    @Column(name = "updated_at")
    private Timestamp updatedAt;

    @ManyToOne()
    @JoinColumn(name = "category_id",referencedColumnName = "category_id")
    @JsonBackReference
    private Category category;


    public int getProductId() {
        return productId;
    }

    public void setProductId(int productId) {
        this.productId = productId;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public UnitType getUnitType() {
        return unitType;
    }

    public void setUnitType(UnitType unitType) {
        this.unitType = unitType;
    }

    public String getProductImg() {
        return productImg;
    }

    public void setProductImg(String productImg) {
        this.productImg = productImg;
    }

    public double getQuantity() {
        return quantity;
    }

    public void setQuantity(double quantity) {
        if (quantity < 0) {
            throw new IllegalArgumentException("Quantity must be greater than or equal to 0");
        }
        this.quantity = quantity;
    }

    public Date getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Date createdAt) {
        this.createdAt = createdAt;
    }

    public Timestamp getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(Timestamp updatedAt) {
        this.updatedAt = updatedAt;
    }

    public Category getCategory() {
        return category;
    }

    public void setCategory(Category category) {
        this.category = category;
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
