package com.accenture.franchise_api.application.useCase;

import com.accenture.franchise_api.domain.exceptions.ResourceNotFoundException;
import com.accenture.franchise_api.domain.port.ProductRepository;
import reactor.core.publisher.Mono;

public class DeleteProductBranchUseCase {
    private final ProductRepository productRepository;

    public DeleteProductBranchUseCase(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    public Mono<Void> execute(Long branchId, Long  productId) {
        return productRepository.findById(productId)
                .filter(p -> p.branchId().equals(branchId))
                .switchIfEmpty(Mono.error(new ResourceNotFoundException(" Producto con ID: "+ productId +" no encontrado en la sucursal")))
                .flatMap(p-> productRepository.deleteById(productId));
    }
}
