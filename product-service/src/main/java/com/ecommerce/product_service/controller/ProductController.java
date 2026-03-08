package com.ecommerce.product_service.controller;

import com.ecommerce.product_service.dto.ProductRequestDTO;
import com.ecommerce.product_service.dto.ProductResponseDTO;
import com.ecommerce.product_service.service.ProductService;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/product")
@RefreshScope
public class ProductController {


    private final ProductService service;

    @Value("${app.maintenance.message: Sistema Operativo}")
    private String maintenanceMessage;

    @PostMapping
    @ResponseStatus( HttpStatus.CREATED )
    public ProductResponseDTO createProduct(@RequestBody @Valid ProductRequestDTO requestDTO) {
        return service.createProduct(requestDTO);
    }

    @GetMapping
    @ResponseStatus( HttpStatus.OK)
    public List<ProductResponseDTO> getAllProducts(HttpServletResponse response){

        response.addHeader("X-Maintenance-Message", maintenanceMessage);
        return service.getAllProducts();
    }

    @GetMapping("/{id}")
    @ResponseStatus( HttpStatus.OK)
    public ProductResponseDTO getProductById(@PathVariable String id){
        return service.getProductById(id);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus( HttpStatus.NO_CONTENT )
    public void deleteProductById(@PathVariable String id){
        service.deleteProductById(id);
    }

    @PutMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public ProductResponseDTO updateProduct(@RequestBody @Valid ProductRequestDTO requestDTO, @PathVariable String id){
        return service.updateProductById(id, requestDTO);
    }


    @GetMapping("/test-fail")
    public void testFail(){
        throw new RuntimeException("La base de datos esta fallando..");
    }
}
