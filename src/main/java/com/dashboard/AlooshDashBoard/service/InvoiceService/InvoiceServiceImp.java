package com.dashboard.AlooshDashBoard.service.InvoiceService;

import com.dashboard.AlooshDashBoard.entity.dto.InvoiceDTO;
import com.dashboard.AlooshDashBoard.entity.dto.InvoiceResponseDTO;
import com.dashboard.AlooshDashBoard.entity.dto.ItemDTO;
import com.dashboard.AlooshDashBoard.entity.dto.PaymentDTO;
import com.dashboard.AlooshDashBoard.entity.enums.PaymentMethod;
import com.dashboard.AlooshDashBoard.entity.enums.PaymentType;
import com.dashboard.AlooshDashBoard.entity.mapper.InvoiceMapper;
import com.dashboard.AlooshDashBoard.entity.models.Invoice;
import com.dashboard.AlooshDashBoard.entity.models.InvoiceItem;
import com.dashboard.AlooshDashBoard.entity.models.Payment;
import com.dashboard.AlooshDashBoard.entity.models.Product;
import com.dashboard.AlooshDashBoard.exceptionHandler.ProductException;
import com.dashboard.AlooshDashBoard.repository.InvoiceRepository;
import com.dashboard.AlooshDashBoard.repository.PaymentRepository;
import com.dashboard.AlooshDashBoard.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
public class InvoiceServiceImp implements InvoiceService {
    @Autowired
    private ProductRepository productRepository;
    @Autowired
    private InvoiceRepository invoiceRepository;
    @Autowired
    PaymentRepository paymentRepository;


    public String generateInvoiceNumber() {
        String lastInvoiceNumber = invoiceRepository.findLastInvoiceNumber();

        if (lastInvoiceNumber == null) {
            return "INV-001";
        }
        int lastNumber = Integer.parseInt(lastInvoiceNumber.split("-")[1]);
        String newInvoiceNumber = String.format("INV-%03d", lastNumber + 1);

        return newInvoiceNumber;
    }

    @Override
    public Invoice createInvoice(InvoiceDTO invoiceDTO) {
        Invoice invoice = new Invoice();
        invoice.setInvoiceNumber(generateInvoiceNumber());
        invoice.setCustomerName(invoiceDTO.getCustomerName());
        invoice.setCustomerPhone(invoiceDTO.getCustomerPhone());
        invoice.setSubtotal(invoiceDTO.getSubtotal());
        invoice.setTotal(invoiceDTO.getTotal());
        invoice.setPaymentMethod(invoiceDTO.getPaymentMethod());

        for(ItemDTO itemDTO : invoiceDTO.getItems()) {
            InvoiceItem invoiceItem = new InvoiceItem();
            Product product = productRepository.findById(itemDTO.getProductId()).orElseThrow(
                    ()-> new ProductException(HttpStatus.NOT_FOUND,"Product Not Found")
            );
            if (itemDTO.getQuantityInMeter()>0){
                double numberOfRolle=product.getQuantity();
                double rollSize=product.getRollSize();
                double quantityInMeters=product.getQuantityInMeters();

                    product.setQuantityInMeters(product.getQuantityInMeters()-itemDTO.getQuantityInMeter());
                    double rolle= product.getQuantityInMeters()/rollSize;
                    product.setQuantity(rolle);

                invoiceItem.setQuantity(itemDTO.getQuantityInMeter());
                invoiceItem.setPrice(product.getPricePerMeters());

            }else {
                invoiceItem.setQuantity(itemDTO.getQuantity());
                product.setQuantity(product.getQuantity()-itemDTO.getQuantity());
                invoiceItem.setPrice(product.getPrice());

            }


            invoiceItem.setInvoice(invoice);
            invoiceItem.setProduct(product);


            invoice.getItems().add(invoiceItem);
        }
        if (invoiceDTO.getPaymentMethod()==PaymentMethod.CASH){
            return invoiceRepository.save(invoice);
        }
        if (invoiceDTO.getPaymentMethod() == PaymentMethod.INSTALLMENTS) {
            invoice.setInitialPayment(invoiceDTO.getInitialPayment());
            Invoice savedInvoice = invoiceRepository.save(invoice);


            for (PaymentDTO paymentDTO : invoiceDTO.getPayments()) {
                Payment payment = new Payment();
                payment.setInvoice(savedInvoice);
                payment.setAmount(paymentDTO.getAmount());


                try {
                    SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
                    Date paymentDate = formatter.parse(paymentDTO.getPaymentDate());
                    payment.setPaymentDate(paymentDate);
                } catch (Exception e) {
                    throw new RuntimeException("Invalid payment date format", e);
                }

                payment.setPaymentType(PaymentType.UNPAID);
                savedInvoice.getPayments().add(payment);
                paymentRepository.save(payment);
            }


            return savedInvoice;
        }
    return null;
    }

    @Override
    public List<InvoiceResponseDTO> findAllInvoices() {
        List<Invoice>invoices=invoiceRepository.findAll();
        List<InvoiceResponseDTO> invoiceResponseDTOS= InvoiceMapper.toDTOList(invoices);
        return invoiceResponseDTOS;
    }




    public double getTotalCashSales() {
        return invoiceRepository.calculateTotalSalesByPaymentMethod(PaymentMethod.CASH);
    }
    public double getTotalPaidPayments() {
        return paymentRepository.calculateTotalPaidPayments(PaymentType.PAID);
    }
    public double getTotalUnPaidPayments() {
        return paymentRepository.calculateTotalPaidPayments(PaymentType.UNPAID);
    }
    public double getTotalInitialPayments() {
        return invoiceRepository.calculateTotalInitialPayments();
    }

    public double getTotalSalesMethodToday() {
        return invoiceRepository.calculateTotalSalesTodayMethod(PaymentMethod.CASH);
    }
    public double getTotalPaidPaymentsToday(){
        return paymentRepository.calculateTotalPaidPaymentsToday(PaymentType.PAID);
    }
    public double getTotalInitialPaymentsToday(){
        return invoiceRepository.calculateTotalInitialPaymentsToday();
    }
    @Override
    public double totalSales() {
        double cashSales = getTotalCashSales();
        double installmentsPayments = getTotalPaidPayments();
        double initialPayments = getTotalInitialPayments();
        return cashSales + installmentsPayments+initialPayments;
    }

    @Override
    public double totalUnPaidPayment() {
        return getTotalUnPaidPayments();
    }

    @Override
    public double totalDalyPayment() {
        return getTotalSalesMethodToday()+getTotalPaidPaymentsToday()+getTotalInitialPaymentsToday();
    }

    @Override
    public int countInvoices() {
        return invoiceRepository.countInvoice();
    }

}
