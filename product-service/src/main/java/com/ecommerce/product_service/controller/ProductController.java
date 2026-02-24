package com.ecommerce.product_service.controller;

import com.ecommerce.product_service.dto.ProductRequestDTO;
import com.ecommerce.product_service.dto.ProductResponseDTO;
import com.ecommerce.product_service.service.ProductService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/product")
public class ProductController {


    private final ProductService service;

    @PostMapping
    @ResponseStatus( HttpStatus.CREATED )
    public ProductResponseDTO createProduct(@RequestBody @Valid ProductRequestDTO requestDTO) {
        return service.createProduct(requestDTO);
    }

    @GetMapping
    @ResponseStatus( HttpStatus.OK)
    public List<ProductResponseDTO> getAllProducts(){
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
