package com.dashboard.AlooshDashBoard.api;

import com.dashboard.AlooshDashBoard.entity.dto.PaymentInstallmentDTO;
import com.dashboard.AlooshDashBoard.exceptionHandler.CategoryException;
import com.dashboard.AlooshDashBoard.exceptionHandler.PaymentException;
import com.dashboard.AlooshDashBoard.exceptionHandler.ProductException;
import com.dashboard.AlooshDashBoard.service.PaymentService.PaymentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api")
public class PaymentApi {
    @Autowired
    private PaymentService paymentService;

    @GetMapping("/payment/installment")
    public ResponseEntity<?> paymentInstallment() {
        try {
            List<PaymentInstallmentDTO>installmentDTOS=paymentService.getPaymentInstallment();
            return ResponseEntity.ok(installmentDTOS);
        }catch (Exception e) {
            return ResponseEntity.ok(e.getMessage());
        }
    }
    @PutMapping("/payment/paid/{paymentID}")
    public ResponseEntity<?>paidPayment(@PathVariable int paymentID){
        try {
            PaymentInstallmentDTO paymentInstallmentDTO=paymentService.payPayment(paymentID);
            return ResponseEntity.ok(paymentInstallmentDTO);
        }catch (Exception ex) {
            Map<String, Object> errorResponse = Map.of(
                    "message",ex.getMessage()
            );
            HttpStatus status = HttpStatus.INTERNAL_SERVER_ERROR;
            if (ex instanceof NullPointerException) {
                status = HttpStatus.BAD_REQUEST;
            }else if (ex instanceof IllegalArgumentException) {
                status = HttpStatus.BAD_REQUEST;
            }else if (ex instanceof PaymentException) {
                status = HttpStatus.NOT_FOUND;
            }
            return ResponseEntity.status(status).body(errorResponse);
        }
    }
}
