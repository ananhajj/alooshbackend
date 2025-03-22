package com.dashboard.AlooshDashBoard.entity.mapper;

import com.dashboard.AlooshDashBoard.entity.dto.*;
import com.dashboard.AlooshDashBoard.entity.models.Invoice;

import java.text.SimpleDateFormat;
import java.util.List;
import java.util.stream.Collectors;

public class InvoiceMapper {
    public static InvoiceResponseDTO toDTO(Invoice invoice) {
        InvoiceResponseDTO dto = new InvoiceResponseDTO();

        dto.setInvoiceId(invoice.getInvoiceId());
        dto.setInvoiceNumber(invoice.getInvoiceNumber());
        dto.setMethod(invoice.getPaymentMethod().name());
        SimpleDateFormat dateFormat = new SimpleDateFormat("d/M/yyyy");
        String formattedDate = dateFormat.format(invoice.getDate());

        dto.setDate(formattedDate);

        List<InvoiceItemDTO> itemDTOs = invoice.getItems().stream().map(item -> {
            InvoiceItemDTO itemDTO = new InvoiceItemDTO();
            itemDTO.setProductId(item.getProduct().getProductId());
            itemDTO.setName(item.getProduct().getProductName());
            itemDTO.setPrice(item.getPrice());
            itemDTO.setQuantity(item.getQuantity());
            itemDTO.setTotalPrice(item.getQuantity() * item.getPrice());
            return itemDTO;
        }).collect(Collectors.toList());
        dto.setItems(itemDTOs);


        CustomerDTO customerDTO = new CustomerDTO();
        customerDTO.setName(invoice.getCustomerName());
        customerDTO.setPhone(invoice.getCustomerPhone());
        dto.setCustomer(customerDTO);

        PaymentResponseDTO paymentDTO = new PaymentResponseDTO();
        paymentDTO.setInitialPayment(invoice.getInitialPayment());
        List<PaymentDetailDTO> paymentDetails = invoice.getPayments().stream().map(payment -> {
            PaymentDetailDTO paymentDetailDTO = new PaymentDetailDTO();
            paymentDetailDTO.setAmount(payment.getAmount());
            paymentDetailDTO.setPaymentDate(payment.getPaymentDate().toString());
            paymentDetailDTO.setStatus(payment.getPaymentType().toString());
            return paymentDetailDTO;
        }).collect(Collectors.toList());
        paymentDTO.setPayments(paymentDetails);
        dto.setPayment(paymentDTO);

        dto.setSubtotal(invoice.getSubtotal());
        dto.setTotal(invoice.getTotal());

        return dto;
    }


    public static List<InvoiceResponseDTO> toDTOList(List<Invoice> invoices) {
        return invoices.stream()
                .map(InvoiceMapper::toDTO)
                .collect(Collectors.toList());
    }
}
