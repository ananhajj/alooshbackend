package com.dashboard.AlooshDashBoard.service.InvoiceService;

import com.dashboard.AlooshDashBoard.entity.dto.InvoiceDTO;
import com.dashboard.AlooshDashBoard.entity.dto.InvoiceResponseDTO;
import com.dashboard.AlooshDashBoard.entity.models.Invoice;

import java.util.List;

public interface InvoiceService {
    Invoice createInvoice(InvoiceDTO invoiceDTO);
    List<InvoiceResponseDTO> findAllInvoices();
    double totalSales();
    double totalUnPaidPayment();
    double totalDalyPayment();

    int countInvoices();

}
