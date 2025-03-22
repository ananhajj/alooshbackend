package com.dashboard.AlooshDashBoard.service.ProductService;

import com.dashboard.AlooshDashBoard.entity.dto.ProductDto;
import com.dashboard.AlooshDashBoard.entity.enums.UnitType;
import com.dashboard.AlooshDashBoard.entity.models.Product;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Map;

public interface ProductService {
    Product addProduct(int categoryId, String productName, double price,double quantity, String unitType,String productImage,double rollSize,double quantityInMeters, double pricePerMeters );
    List<ProductDto> getAllProducts();
    Product getProductById(int productId);
    Product updateProduct(int productId, ProductDto productDto,MultipartFile file);
    Product updateProductImage(int productId, MultipartFile productImage);
    void deleteProduct(int productId);
    double getTotalProductQuantity();
    List<Map<String, Object>> getTopSellingProducts();


}
