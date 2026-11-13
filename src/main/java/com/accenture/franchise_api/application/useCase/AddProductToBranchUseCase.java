package com.accenture.franchise_api.application.useCase;

import com.accenture.franchise_api.domain.exceptions.ResourceNotFoundException;
import com.accenture.franchise_api.domain.model.Product;
import com.accenture.franchise_api.domain.port.BranchRepository;
import com.accenture.franchise_api.domain.port.ProductRepository;
import reactor.core.publisher.Mono;

public class AddProductToBranchUseCase {
    private BranchRepository branchRepository;
    private ProductRepository productRepository;

    public AddProductToBranchUseCase(BranchRepository branchRepository, ProductRepository productRepository) {
        this.branchRepository = branchRepository;
        this.productRepository = productRepository;
    }

    public Mono<Product> execute(Long branchId, String productName, Integer Stock) {
        return branchRepository.findById(branchId)
                .switchIfEmpty(Mono.error(new ResourceNotFoundException("Sucursal no encontrado con ID: " + branchId)))
                .map(branch -> Product.create(branch.id(), productName, Stock))
                .flatMap(productRepository::save);
    }
}
