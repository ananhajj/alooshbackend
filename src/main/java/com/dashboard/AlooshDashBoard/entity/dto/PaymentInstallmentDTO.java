package com.dashboard.AlooshDashBoard.entity.dto;

import com.dashboard.AlooshDashBoard.entity.enums.PaymentType;

import java.text.SimpleDateFormat;
import java.util.Date;

public class PaymentInstallmentDTO {
    private int paymentId;
    private String invoiceNumber;
    private String customerName;
    private double amount;
    private String dueDate;
    private String type;
    private String status;

    public PaymentInstallmentDTO(int paymentId,String invoiceNumber, String customerName, double amount, Date dueDate, PaymentType type) {
        this.paymentId = paymentId;
        this.invoiceNumber = invoiceNumber;
        this.customerName = customerName;
        this.amount = amount;
        this.dueDate = new SimpleDateFormat("yyyy-MM-dd").format(dueDate);
        this.type = "installment";
        this.status = (type == PaymentType.PAID) ? "paid" : "pending";
    }

    public int getPaymentId() {
        return paymentId;
    }

    public void setPaymentId(int paymentId) {
        this.paymentId = paymentId;
    }

    public String getInvoiceNumber() {
        return invoiceNumber;
    }

    public void setInvoiceNumber(String invoiceNumber) {
        this.invoiceNumber = invoiceNumber;
    }

    public String getCustomerName() {
        return customerName;
    }

    public void setCustomerName(String customerName) {
        this.customerName = customerName;
    }

    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }

    public String getDueDate() {
        return dueDate;
    }

    public void setDueDate(String dueDate) {
        this.dueDate = dueDate;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
