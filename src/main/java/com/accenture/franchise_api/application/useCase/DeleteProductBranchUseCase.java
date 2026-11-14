package com.accenture.franchise_api.application.useCase;

import com.accenture.franchise_api.domain.exceptions.ResourceNotFoundException;
import com.accenture.franchise_api.domain.port.ProductRepository;
import reactor.core.publisher.Mono;

public class DeleteProductBranchUseCase {
    private final ProductRepository productRepository;

    public DeleteProductBranchUseCase(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    public Mono<Void> execute(Long productId) {
        return productRepository.findById(productId)
                .switchIfEmpty(Mono.error(new ResourceNotFoundException("Producto no encontrado con ID: " + productId)))
                .flatMap(product -> productRepository.deleteById(product.id()));
    }
}
