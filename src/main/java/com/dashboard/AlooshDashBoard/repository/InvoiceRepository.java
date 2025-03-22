package com.dashboard.AlooshDashBoard.repository;

import com.dashboard.AlooshDashBoard.entity.enums.PaymentMethod;
import com.dashboard.AlooshDashBoard.entity.models.Invoice;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface InvoiceRepository extends JpaRepository<Invoice, Integer> {
    @Query("SELECT i.invoiceNumber FROM Invoice i ORDER BY i.invoiceId DESC LIMIT 1")
    String findLastInvoiceNumber();

    @Query("SELECT COALESCE(SUM(i.total), 0) FROM Invoice i WHERE i.paymentMethod = :paymentMethod")
    Double calculateTotalSalesByPaymentMethod(@Param("paymentMethod") PaymentMethod paymentMethod);

    @Query("SELECT COALESCE(SUM(i.initialPayment), 0) FROM Invoice i")
    Double calculateTotalInitialPayments();


    @Query("SELECT COALESCE(SUM(i.initialPayment), 0) FROM Invoice i WHERE DATE(i.date) = CURRENT_DATE")
    Double calculateTotalInitialPaymentsToday();

    @Query("SELECT COALESCE(SUM(i.total), 0) FROM Invoice i WHERE DATE(i.date) = CURRENT_DATE AND i.paymentMethod = :paymentMethod")
    Double calculateTotalSalesTodayMethod(@Param("paymentMethod") PaymentMethod paymentMethod);

    @Query("SELECT COUNT(i) FROM Invoice i")
    int countInvoice();

}
