package com.dashboard.AlooshDashBoard.api;

import com.dashboard.AlooshDashBoard.Cloudinary.CloudinaryService;
import com.dashboard.AlooshDashBoard.entity.dto.ProductDto;
import com.dashboard.AlooshDashBoard.entity.enums.UnitType;
import com.dashboard.AlooshDashBoard.entity.models.Product;
import com.dashboard.AlooshDashBoard.exceptionHandler.CategoryException;
import com.dashboard.AlooshDashBoard.exceptionHandler.ProductException;
import com.dashboard.AlooshDashBoard.service.ProductService.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api")
public class ProductApi {
    @Autowired
    private ProductService productService;
    @Autowired
    private CloudinaryService cloudinaryService;
    @PostMapping("/product")
    public ResponseEntity<?> newProduct(@RequestParam Map<String,String>params,
                                        @RequestParam("image")MultipartFile file){
        try {
            int categoryId = Integer.parseInt(params.get("categoryId"));
            String productName = params.get("productName");
            double price = Double.parseDouble(params.getOrDefault("price", "0.0"));
            double quantity = Double.parseDouble(params.getOrDefault("quantity", "0.0"));
            String unitType = params.get("unitType").toUpperCase();
            System.out.println("unitType: " + unitType);



            double rollSize = params.get("rollSize") != null && !params.get("rollSize").isEmpty() ? Double.parseDouble(params.get("rollSize")) : 0.0;
            double quantityInMeters = params.get("quantityInMeters") != null && !params.get("quantityInMeters").isEmpty() ? Double.parseDouble(params.get("quantityInMeters")) : 0.0;
            double pricePerMeters = params.get("pricePerMeters") != null && !params.get("pricePerMeters").isEmpty() ? Double.parseDouble(params.get("pricePerMeters")) : 0.0;


            String imgUrl = cloudinaryService.uploadProductFile(file);
            Product product = productService.addProduct(categoryId, productName, price, quantity, unitType, imgUrl,
                     rollSize, quantityInMeters, pricePerMeters);
            ProductDto productDto=new ProductDto(product);
            Map<String, Object> response = Map.of(
                    "status", "success",
                    "message", "Product added successfully",
                    "product", productDto
            );
            return ResponseEntity.ok(response);
        }catch (Exception ex) {
            Map<String, Object> errorResponse = Map.of(
                    "status", "error",
                    "message", ex.getMessage()
            );
            HttpStatus status = HttpStatus.INTERNAL_SERVER_ERROR;
            if (ex instanceof NullPointerException) {
                status = HttpStatus.BAD_REQUEST;
            } else if (ex instanceof IllegalArgumentException) {
                status = HttpStatus.BAD_REQUEST;
            } else if (ex instanceof CategoryException) {
                status = HttpStatus.NOT_FOUND;
            }
            return ResponseEntity.status(status).body(errorResponse);
        }
    }

    @GetMapping("/products")
    public ResponseEntity<?> getAllProducts() {
        try {
            List<ProductDto>products=productService.getAllProducts();
            Map<String, Object> response = Map.of(
                    "status","success",
                    "products", products
            );
            return ResponseEntity.ok(response);
        }catch (Exception ex) {
            Map<String, Object> errorResponse = Map.of(
                    "status","error",
                    "message", ex.getMessage()
            );
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorResponse);

        }
    }

    @GetMapping("/product/{productId}")
    public ResponseEntity<?>getProductById(@PathVariable int productId) {
        try {
            Product product=productService.getProductById(productId);
            Map<String, Object> response = Map.of(
                    "status","success",
                    "product", product
            );
            return ResponseEntity.ok(response);

        }catch (Exception ex) {
            Map<String, Object> errorResponse = Map.of(
                    "message",ex.getMessage()
            );
            HttpStatus status = HttpStatus.INTERNAL_SERVER_ERROR;
            if (ex instanceof NullPointerException) {
                status = HttpStatus.BAD_REQUEST;
            }else if (ex instanceof IllegalArgumentException) {
                status = HttpStatus.BAD_REQUEST;
            }else if (ex instanceof ProductException) {
                status = HttpStatus.NOT_FOUND;
            }
            return ResponseEntity.status(status).body(errorResponse);
        }
    }
    @PutMapping("/product/{productId}")
    public ResponseEntity<?> updateProduct(@PathVariable int productId,
                                           @ModelAttribute  ProductDto productDto,
                                           @RequestPart(value = "image", required = false) MultipartFile file
                                          ) {
        try {
            Product updateProduct=productService.updateProduct(productId,productDto,file);
            ProductDto productDto1=new ProductDto(updateProduct);
            return ResponseEntity.ok(productDto1);
        }catch (Exception ex) {
            Map<String, Object> errorResponse = Map.of(
                    "message",ex.getMessage()
            );
            HttpStatus status = HttpStatus.INTERNAL_SERVER_ERROR;
            if (ex instanceof NullPointerException) {
                status = HttpStatus.BAD_REQUEST;
            }else if (ex instanceof IllegalArgumentException) {
                status = HttpStatus.BAD_REQUEST;
            }else if (ex instanceof ProductException) {
                status = HttpStatus.NOT_FOUND;
            }else if (ex instanceof CategoryException) {
                status = HttpStatus.BAD_REQUEST;
            }
            return ResponseEntity.status(status).body(errorResponse);
        }

    }
    @PutMapping("/product/image/{id}")
    public ResponseEntity<?> updateImageProduct(@PathVariable int id,
                                                @RequestParam("image") MultipartFile file) {
        try {
            Product updateProduct=productService.updateProductImage(id,file);
            return ResponseEntity.ok(updateProduct);
        }catch (Exception ex) {
            Map<String, Object> errorResponse = Map.of(
                    "message",ex.getMessage()
            );
            HttpStatus status = HttpStatus.INTERNAL_SERVER_ERROR;
            if (ex instanceof NullPointerException) {
                status = HttpStatus.BAD_REQUEST;
            }else if (ex instanceof IllegalArgumentException) {
                status = HttpStatus.BAD_REQUEST;
            }else if (ex instanceof ProductException) {
                status = HttpStatus.NOT_FOUND;
            }
            return ResponseEntity.status(status).body(errorResponse);
        }

    }

    @DeleteMapping("/product/{productId}")
    public ResponseEntity<?> deleteProduct(@PathVariable int productId) {
        try {
            productService.deleteProduct(productId);
            Map<String, Object> response = Map.of(
                    "status","success",
                    "message","product deleted successfully"
            );
            return ResponseEntity.ok(response);
        }catch (Exception ex) {
            Map<String, Object> errorResponse = Map.of(
                    "message",ex.getMessage()
            );
            HttpStatus status = HttpStatus.INTERNAL_SERVER_ERROR;
            if (ex instanceof NullPointerException) {
                status = HttpStatus.BAD_REQUEST;
            }else if (ex instanceof IllegalArgumentException) {
                status = HttpStatus.BAD_REQUEST;
            }else if (ex instanceof ProductException) {
                status = HttpStatus.NOT_FOUND;
            }
            return ResponseEntity.status(status).body(errorResponse);
        }
    }



}
