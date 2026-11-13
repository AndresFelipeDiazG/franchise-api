package com.accenture.franchise_api.application.useCase;

import com.accenture.franchise_api.domain.model.Franchise;
import com.accenture.franchise_api.domain.port.FranchiseRepository;
import reactor.core.publisher.Mono;

public class CreateFranchiseUseCase {
    private final FranchiseRepository franchiseRepository;

    public CreateFranchiseUseCase(FranchiseRepository franchiseRepository) {
        this.franchiseRepository = franchiseRepository;
    }

    public Mono<Franchise> execute(String name) {
        return Mono.fromCallable(() -> Franchise.create(name))
                .flatMap(franchiseRepository::save);
    }
}
