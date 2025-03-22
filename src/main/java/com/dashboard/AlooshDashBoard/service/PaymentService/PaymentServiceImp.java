package com.dashboard.AlooshDashBoard.service.PaymentService;

import com.dashboard.AlooshDashBoard.entity.dto.PaymentInstallmentDTO;
import com.dashboard.AlooshDashBoard.entity.enums.PaymentType;
import com.dashboard.AlooshDashBoard.entity.enums.UnitType;
import com.dashboard.AlooshDashBoard.entity.models.Payment;
import com.dashboard.AlooshDashBoard.entity.models.Product;
import com.dashboard.AlooshDashBoard.exceptionHandler.PaymentException;
import com.dashboard.AlooshDashBoard.exceptionHandler.ProductException;
import com.dashboard.AlooshDashBoard.repository.InvoiceItemRepository;
import com.dashboard.AlooshDashBoard.repository.PaymentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class PaymentServiceImp implements PaymentService {
    @Autowired
    private PaymentRepository paymentRepository;
    @Autowired
    private InvoiceItemRepository invoiceItemRepository;

    @Override
    public List<PaymentInstallmentDTO> getPaymentInstallment() {
        List<Payment>payments = paymentRepository.findAll();
        List<PaymentInstallmentDTO> installments  = payments.stream()
                .map(payment -> new PaymentInstallmentDTO(
                        payment.getPaymentId(),
                        payment.getInvoice().getInvoiceNumber(),
                        payment.getInvoice().getCustomerName(),
                        payment.getAmount(),
                        payment.getPaymentDate(),
                        payment.getPaymentType()
                )).collect(Collectors.toList());
        return installments ;
    }

    @Override
    public PaymentInstallmentDTO payPayment(int paymentId) {
        Payment payment=paymentRepository.findById(paymentId).orElseThrow(
                ()->new PaymentException(HttpStatus.NOT_FOUND,"Paymet Not Found")
        );


        payment.setPaymentType(PaymentType.PAID);
        paymentRepository.save(payment);
        PaymentInstallmentDTO paymentInstallmentDTO=new PaymentInstallmentDTO
                (
                        payment.getPaymentId(),
                        payment.getInvoice().getInvoiceNumber(),
                        payment.getInvoice().getCustomerName(),
                        payment.getAmount(),
                        payment.getPaymentDate(),
                        payment.getPaymentType()
                );
        return paymentInstallmentDTO;
    }


}
