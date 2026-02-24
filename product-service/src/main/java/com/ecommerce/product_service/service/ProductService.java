package com.ecommerce.product_service.service;

import com.ecommerce.product_service.dto.ProductRequestDTO;
import com.ecommerce.product_service.dto.ProductResponseDTO;
import com.ecommerce.product_service.model.Product;

import java.util.List;

public interface ProductService {


    ProductResponseDTO createProduct(ProductRequestDTO requestDTO);
    List<ProductResponseDTO> getAllProducts();
    ProductResponseDTO getProductById(String id);
    ProductResponseDTO updateProductById(String id, ProductRequestDTO productRequestDTO);
    void deleteProductById(String id);
}
