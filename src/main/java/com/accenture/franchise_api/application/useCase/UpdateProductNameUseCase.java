package com.accenture.franchise_api.application.useCase;

import com.accenture.franchise_api.domain.exceptions.ResourceNotFoundException;
import com.accenture.franchise_api.domain.model.Product;
import com.accenture.franchise_api.domain.port.ProductRepository;
import reactor.core.publisher.Mono;

public class UpdateProductNameUseCase {
    private ProductRepository productRepository;

    public UpdateProductNameUseCase(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    public Mono<Product> execute(long productId, String newProductName) {
        return productRepository.findById(productId)
                .switchIfEmpty(Mono.error(new ResourceNotFoundException("Producto no encontrado con ID: " + productId)))
                .map(existProduct -> existProduct.withName(newProductName))
                .flatMap(productRepository::save);
    }
}
