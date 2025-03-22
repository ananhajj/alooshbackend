package com.dashboard.AlooshDashBoard.entity.dto;

import com.dashboard.AlooshDashBoard.entity.enums.PaymentType;

public class PaymentDTO {
    private double amount;
    private String paymentDate;


    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }

    public String getPaymentDate() {
        return paymentDate;
    }

    public void setPaymentDate(String paymentDate) {
        this.paymentDate = paymentDate;
    }


}
