package com.dashboard.AlooshDashBoard.entity.dto;

import java.util.List;

public class InvoiceResponseDTO {
    private int invoiceId;
    private String invoiceNumber;
    private String method;
    private String date;
    private List<InvoiceItemDTO> items;
    private double subtotal;
    private double total;
    private CustomerDTO customer;
    private PaymentResponseDTO payment;

    public String getInvoiceNumber() {
        return invoiceNumber;
    }

    public void setInvoiceNumber(String invoiceNumber) {
        this.invoiceNumber = invoiceNumber;
    }

    public int getInvoiceId() {
        return invoiceId;
    }

    public void setInvoiceId(int invoiceId) {
        this.invoiceId = invoiceId;
    }

    public String getMethod() {
        return method;
    }

    public void setMethod(String method) {
        this.method = method;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public double getSubtotal() {
        return subtotal;
    }

    public void setSubtotal(double subtotal) {
        this.subtotal = subtotal;
    }

    public List<InvoiceItemDTO> getItems() {
        return items;
    }

    public void setItems(List<InvoiceItemDTO> items) {
        this.items = items;
    }

    public double getTotal() {
        return total;
    }

    public void setTotal(double total) {
        this.total = total;
    }

    public CustomerDTO getCustomer() {
        return customer;
    }

    public void setCustomer(CustomerDTO customer) {
        this.customer = customer;
    }

    public void setPayment(PaymentResponseDTO payment) {
        this.payment = payment;
    }

    public PaymentResponseDTO getPayment() {
        return payment;
    }
}
