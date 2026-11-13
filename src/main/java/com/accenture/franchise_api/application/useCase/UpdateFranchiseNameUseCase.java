package com.accenture.franchise_api.application.useCase;

import com.accenture.franchise_api.domain.exceptions.ResourceNotFoundException;
import com.accenture.franchise_api.domain.model.Franchise;
import com.accenture.franchise_api.domain.port.FranchiseRepository;
import reactor.core.publisher.Mono;

public class UpdateFranchiseNameUseCase {
    private final FranchiseRepository franchiseRepository;

    public UpdateFranchiseNameUseCase(FranchiseRepository franchiseRepository) {
        this.franchiseRepository = franchiseRepository;
    }

    public Mono<Franchise> execute(Long franchiseId, String newName) {
        return  franchiseRepository.findById(franchiseId)
                .switchIfEmpty(Mono.error(new ResourceNotFoundException("Franquicia no encontrada con ID: " + franchiseId)))
                .map(existingFranchise -> existingFranchise.withName(newName))
                .flatMap(franchiseRepository::save);
    }
}
