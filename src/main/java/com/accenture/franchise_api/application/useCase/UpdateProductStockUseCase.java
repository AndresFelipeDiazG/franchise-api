package com.accenture.franchise_api.application.useCase;

import com.accenture.franchise_api.domain.exceptions.ResourceNotFoundException;
import com.accenture.franchise_api.domain.model.Product;
import com.accenture.franchise_api.domain.port.ProductRepository;
import reactor.core.publisher.Mono;

public class UpdateProductStockUseCase {
    private ProductRepository productRepository;

    public UpdateProductStockUseCase(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    public Mono<Product> execute(Long productId, Integer NewStock) {
        return productRepository.findById(productId)
                .switchIfEmpty(Mono.error(new ResourceNotFoundException("Producto no encontrado con ID: " + productId)))
                .map(existProduct -> existProduct.withStock(NewStock))
                .flatMap(productRepository::save);
    }
}
