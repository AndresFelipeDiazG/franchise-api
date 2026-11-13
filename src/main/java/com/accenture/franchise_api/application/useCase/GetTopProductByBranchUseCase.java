package com.accenture.franchise_api.application.useCase;

import com.accenture.franchise_api.domain.exceptions.ResourceNotFoundException;
import com.accenture.franchise_api.domain.model.BranchTopProduct;
import com.accenture.franchise_api.domain.port.BranchRepository;
import com.accenture.franchise_api.domain.port.FranchiseRepository;
import com.accenture.franchise_api.domain.port.ProductRepository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public class GetTopProductByBranchUseCase {
    private final FranchiseRepository franchiseRepository;
    private final ProductRepository productRepository;

    public GetTopProductByBranchUseCase(FranchiseRepository franchiseRepository, BranchRepository branchRepository, ProductRepository productRepository) {
        this.franchiseRepository = franchiseRepository;
        this.productRepository = productRepository;
    }

    public Flux<BranchTopProduct> execute(Long franchiseId) {
        return franchiseRepository.findById(franchiseId)
                .switchIfEmpty(Mono.error(new ResourceNotFoundException(" Franquicia no encontrada con ID: " + franchiseId)))
                .flatMapMany(franchise -> productRepository.findTopProductsByFranchiseId(franchise.id()));
    }
}
