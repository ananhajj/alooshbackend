package com.dashboard.AlooshDashBoard.entity.dto;

public class ItemDTO {
    private int productId;
    private int quantity;
    private int quantityInMeter;

    public int getQuantityInMeter() {
        return quantityInMeter;
    }

    public void setQuantityInMeter(int quantityInMeter) {
        this.quantityInMeter = quantityInMeter;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }
    public int getProductId() {
        return productId;
    }

    public void setProductId(int productId) {
        this.productId = productId;
    }
}
