package com.dashboard.AlooshDashBoard.entity.dto;

import java.util.List;

public class PaymentResponseDTO {
    private double initialPayment;
    private List<PaymentDetailDTO> payments;

    public double getInitialPayment() {
        return initialPayment;
    }

    public void setInitialPayment(double initialPayment) {
        this.initialPayment = initialPayment;
    }

    public List<PaymentDetailDTO> getPayments() {
        return payments;
    }

    public void setPayments(List<PaymentDetailDTO> payments) {
        this.payments = payments;
    }
}
