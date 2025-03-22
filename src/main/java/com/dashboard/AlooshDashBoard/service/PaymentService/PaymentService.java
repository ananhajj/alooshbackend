package com.dashboard.AlooshDashBoard.service.PaymentService;

import com.dashboard.AlooshDashBoard.entity.dto.PaymentInstallmentDTO;

import java.util.List;

public interface PaymentService {
    List<PaymentInstallmentDTO> getPaymentInstallment();
    PaymentInstallmentDTO payPayment(int paymentId);

}
