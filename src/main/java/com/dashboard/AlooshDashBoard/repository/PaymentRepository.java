package com.dashboard.AlooshDashBoard.repository;

import com.dashboard.AlooshDashBoard.entity.enums.PaymentMethod;
import com.dashboard.AlooshDashBoard.entity.enums.PaymentType;
import com.dashboard.AlooshDashBoard.entity.models.Payment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface PaymentRepository  extends JpaRepository<Payment, Integer> {
    @Query("SELECT COALESCE(SUM(p.amount), 0) FROM Payment p WHERE p.paymentType = :paymentType")
    double calculateTotalPaidPayments(@Param("paymentType") PaymentType paymentType);

    @Query("SELECT COALESCE(SUM(p.amount), 0) FROM Payment p WHERE p.paymentType = :paymentType AND DATE(p.paymentDate) = CURRENT_DATE")
    double calculateTotalPaidPaymentsToday(@Param("paymentType") PaymentType paymentType);


}
