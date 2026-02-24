package com.ecommerce.product_service.service.impl;

import com.ecommerce.product_service.dto.ProductRequestDTO;
import com.ecommerce.product_service.dto.ProductResponseDTO;
import com.ecommerce.product_service.exception.ResourceNotFoundException;
import com.ecommerce.product_service.mapper.ProductMapper;
import com.ecommerce.product_service.model.Product;
import com.ecommerce.product_service.repository.ProductRepository;
import com.ecommerce.product_service.service.ProductService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Slf4j
public class ProductServiceImpl implements ProductService {

    private final ProductRepository repository;

    private final ProductMapper mapper;

    @Override
    public ProductResponseDTO createProduct(ProductRequestDTO requestDTO) {
        log.info("Creando un nuevo producto: {}", requestDTO.name());
        Product product = mapper.toProduct(requestDTO);

        Product productSave = repository.save(product);
        log.info("Producto creado exitosamente con ID: {}", productSave.getId());

        return mapper.toProductResponseDto(productSave);
    }

    @Override
    public List<ProductResponseDTO> getAllProducts() {
        log.info("Obteniendo lista de todos los productos");
        return repository.findAll().stream()
                .map(mapper::toProductResponseDto)
                .toList();

    }

    @Override
    public ProductResponseDTO getProductById(String id) {
        log.info("Buscando producto con ID: {}", id);
        Product product = repository.findById(id).orElseThrow(
                () -> {
                    log.error("Producto no encontrado con ID: {}", id);
                    return new ResourceNotFoundException("Product", "id", id);
                }
        );

        return mapper.toProductResponseDto(product);

    }

    @Override
    public ProductResponseDTO updateProductById(String id, ProductRequestDTO productRequestDTO) {
        log.info("Actualizando producto con ID: {}", id);
        Product product = repository.findById(id).orElseThrow(
                () -> {
                    log.error("No se pudo actualizar. Producto no encontrado con ID: {}", id);
                    return new ResourceNotFoundException("Product", "id", id);
                }
        );

        mapper.updateProductFromRequest(productRequestDTO, product);

        Product productToUpdate =  repository.save(product);
        log.info("Producto con ID: {} actualizado exitosamente", id);


        return mapper.toProductResponseDto(productToUpdate);

    }

    @Override
    public void deleteProductById(String id) {
        log.info("Eliminando producto con ID: {}", id);
        if(!repository.existsById(id)) {
            log.error("No se pudo eliminar. Producto no encontrado con ID: {}", id);
            throw new ResourceNotFoundException("Product", "id", id);
        }
        repository.deleteById(id);
        log.info("Producto con ID: {} eliminado exitosamente", id);
    }
}
