package com.dashboard.AlooshDashBoard.service.ProductService;

import com.dashboard.AlooshDashBoard.Cloudinary.CloudinaryService;
import com.dashboard.AlooshDashBoard.entity.dto.ProductDto;
import com.dashboard.AlooshDashBoard.entity.enums.UnitType;
import com.dashboard.AlooshDashBoard.entity.models.Category;
import com.dashboard.AlooshDashBoard.entity.models.Product;
import com.dashboard.AlooshDashBoard.exceptionHandler.CategoryException;
import com.dashboard.AlooshDashBoard.exceptionHandler.ProductException;
import com.dashboard.AlooshDashBoard.repository.CategoryRepository;
import com.dashboard.AlooshDashBoard.repository.InvoiceItemRepository;
import com.dashboard.AlooshDashBoard.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class ProductServiceImp implements ProductService {
    @Autowired
    private ProductRepository productRepository;
    @Autowired
    private CategoryRepository categoryRepository;
    @Autowired
    private CloudinaryService cloudinaryService;
    @Autowired
    private InvoiceItemRepository invoiceItemRepository;

    @Override
    public Product addProduct(int categoryId, String productName, double price, double quantity, String unitType, String productImage,double rollSize,double quantityInMeters, double pricePerMeters ) {
        Category category=categoryRepository.findById(categoryId).orElseThrow(
                ()-> new CategoryException(HttpStatus.NOT_FOUND,"Category Not Found")
        );

        if(productRepository.existsProductByProductName(productName)){
            throw new ProductException(HttpStatus.CONFLICT,"Product Name Already Exists");
        }
        UnitType unit=null;
        try {
            unit=UnitType.valueOf(unitType);
        }catch (IllegalArgumentException e){
            throw new ProductException(HttpStatus.BAD_REQUEST,"Unit Type Not Found");
        }
        Product product=new Product();
        product.setProductName(productName);
        product.setCategory(category);
        product.setPrice(price);
        product.setQuantity(quantity);
        product.setUnitType(unit);
        product.setProductImg(productImage);
        product.setRollSize(rollSize);
        product.setQuantityInMeters(quantityInMeters);
        product.setPricePerMeters(pricePerMeters);
        return productRepository.save(product);


    }

    @Override
    public List<ProductDto> getAllProducts() {
       return productRepository.findAll().stream().map(
               product->{
                   ProductDto productDto=new ProductDto();
                   productDto.setProductId(product.getProductId());
                   productDto.setName(product.getProductName());
                   productDto.setPrice(product.getPrice());
                   productDto.setQuantity(product.getQuantity());
                   productDto.setUnitType(product.getUnitType().toString());
                   productDto.setCategoryId(product.getCategory().getCategoryId());
                   productDto.setImageUrl(product.getProductImg());
                   productDto.setRollSize(product.getRollSize());
                   productDto.setQuantityInMeters(product.getQuantityInMeters());
                   productDto.setPricePerMeters(product.getPricePerMeters());
                   return productDto;
               }
       ).collect(Collectors.toList());
    }

    @Override
    public Product getProductById(int productId) {

        Product product=productRepository.findById(productId).orElseThrow(
                ()-> new ProductException(HttpStatus.NOT_FOUND,"Product Not Found")
        );
        return product;
    }

    @Override
    public Product updateProduct(int productId, ProductDto productDto,MultipartFile file) {
        Product product=productRepository.findById(productId).orElseThrow(
                ()-> new ProductException(HttpStatus.NOT_FOUND,"Product Not Found")
        );

        if (productDto.getName()!=null) {
            product.setProductName(productDto.getName());
        }
        if (productDto.getPrice()!=null) {
            product.setPrice(productDto.getPrice());
        }
        if (productDto.getQuantity()!=null) {
            product.setQuantity(productDto.getQuantity());
        }
        if(productDto.getQuantityInMeters()!=0.0){
            product.setQuantityInMeters(productDto.getQuantityInMeters());
        }
        if (productDto.getPricePerMeters()!=0.0){
            product.setPricePerMeters(productDto.getPricePerMeters());
        }
        if (productDto.getRollSize()!=0.0){
            product.setRollSize(productDto.getRollSize());
        }


        if(file!=null&&!file.isEmpty()){
            try {
                String image=cloudinaryService.uploadProductFile(file);
                product.setProductImg(image);
            }catch (Exception e){
                throw new ProductException(HttpStatus.INTERNAL_SERVER_ERROR,"Image Upload Failed");
            }
        }

        if (productDto.getUnitType() != null) {
            product.setUnitType(UnitType.valueOf(productDto.getUnitType().toUpperCase()));
        }
        if(productDto.getCategoryId()>0){
            Category category=categoryRepository.findById(productDto.getCategoryId()).orElseThrow(
                    ()-> new CategoryException(HttpStatus.NOT_FOUND,"Category Not Found")
            );
            product.setCategory(category);
        }
        return productRepository.save(product);
    }

    @Override
    public Product updateProductImage(int productId, MultipartFile productImage) {
        Product product=productRepository.findById(productId).orElseThrow(
                ()-> new ProductException(HttpStatus.NOT_FOUND,"Product Not Found")
        );
        try {
            String image=cloudinaryService.uploadProductFile(productImage);
            product.setProductImg(image);
        }catch (Exception e){
            throw new ProductException(HttpStatus.INTERNAL_SERVER_ERROR,"Image Upload Failed");
        }
        return productRepository.save(product);
    }

    @Override
    public void deleteProduct(int productId) {
        Product product=productRepository.findById(productId).orElseThrow(
                ()-> new ProductException(HttpStatus.NOT_FOUND,"Product Not Found")
        );
        productRepository.delete(product);
    }

    @Override
    public double getTotalProductQuantity() {
        return productRepository.calculateTotalProductQuantity();
    }
    @Override
    public List<Map<String, Object>> getTopSellingProducts() {
        List<Object[]> results = invoiceItemRepository.findTopSellingProducts();
        List<Map<String, Object>> topProducts = new ArrayList<>();

        for (Object[] row : results) {
            Map<String, Object> productData = new HashMap<>();
            productData.put("id", row[0]);
            productData.put("name", row[1]);
            productData.put("category", row[2]);
            productData.put("price", row[3]);
            productData.put("image", row[4]);
            productData.put("sales", row[5]);

            topProducts.add(productData);
        }

        return topProducts;
    }
}
