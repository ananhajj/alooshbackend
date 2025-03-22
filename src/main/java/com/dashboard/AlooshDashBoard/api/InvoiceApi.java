package com.dashboard.AlooshDashBoard.api;

import com.dashboard.AlooshDashBoard.entity.dto.DashBoardInfoDTO;
import com.dashboard.AlooshDashBoard.entity.dto.InvoiceDTO;
import com.dashboard.AlooshDashBoard.entity.dto.InvoiceResponseDTO;
import com.dashboard.AlooshDashBoard.entity.models.Invoice;
import com.dashboard.AlooshDashBoard.entity.models.Product;
import com.dashboard.AlooshDashBoard.service.CategoryService.CategoryService;
import com.dashboard.AlooshDashBoard.service.InvoiceService.InvoiceService;
import com.dashboard.AlooshDashBoard.service.ProductService.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api")
public class InvoiceApi {
    @Autowired
    private InvoiceService invoiceService;
    @Autowired
    private ProductService productService;
    @Autowired
    private CategoryService categoryService;

    @PostMapping("/invoice")
    public ResponseEntity<?>addInvoice(@RequestBody InvoiceDTO invoiceDTO) {
        try {
            Invoice invoice = invoiceService.createInvoice(invoiceDTO);
            Map<String, Object> map = Map.of("invoice", invoice);
            return new ResponseEntity<>(map, HttpStatus.CREATED);
        }catch (Exception ex){
            Map<String, Object> errorResponse = Map.of(
                    "message", ex.getMessage()
            );
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(errorResponse);
        }
    }
    @GetMapping("/invoice")
    public ResponseEntity<?> getInvoice() {
        try {
            List<InvoiceResponseDTO> invoiceResponseDTOList=invoiceService.findAllInvoices();
            return new ResponseEntity<>(invoiceResponseDTOList, HttpStatus.OK);
        }catch (Exception ex){
            Map<String, Object> errorResponse = Map.of(
                    "message", ex.getMessage()
            );
            return new ResponseEntity<>(errorResponse, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/dashboard")
    public ResponseEntity<?> getInfoDashboard() {
        try {
            double totalSales=invoiceService.totalSales();
            double unPaid=invoiceService.totalUnPaidPayment();
            double totalDay=invoiceService.totalDalyPayment();
            double totalProduct=productService.getTotalProductQuantity();
            int roundedTotalProduct = (int) Math.round(totalProduct);
            int  totalCategory=categoryService.totalCategory();
            int totalInvoice=invoiceService.countInvoices();
            List<Map<String, Object>> topSalesProducts = productService.getTopSellingProducts();
            List<Map<String, Object>> categorySales = categoryService.getSalesPercentageByCategory();
            DashBoardInfoDTO data=new DashBoardInfoDTO();
            data.setTotalSales(totalSales);
            data.setUnPaidPayment(unPaid);
            data.setTotalTodayPayment(totalDay);
            data.setTotalProduct(roundedTotalProduct);
            data.setTotalCategory(totalCategory);
            data.setCountInvoice(totalInvoice);
            data.setTopSalesProducts(topSalesProducts);
            data.setCategorySales(categorySales);
            return new ResponseEntity<>(data, HttpStatus.OK);
        }catch (Exception ex){
            Map<String, Object> errorResponse = Map.of(
                    "message", ex.getMessage()
            );
            return new ResponseEntity<>(errorResponse, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
