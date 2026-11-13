package com.accenture.franchise_api.application.useCase;

import com.accenture.franchise_api.domain.exceptions.ResourceNotFoundException;
import com.accenture.franchise_api.domain.model.Branch;
import com.accenture.franchise_api.domain.port.BranchRepository;
import reactor.core.publisher.Mono;

public class UpdateBranchNameUseCase {
    private final BranchRepository branchRepository;

    public UpdateBranchNameUseCase(BranchRepository branchRepository) {
        this.branchRepository = branchRepository;
    }

    public Mono<Branch> execute(Long branchId, String newName) {
        return branchRepository.findById(branchId)
                .switchIfEmpty(Mono.error(new ResourceNotFoundException("Sucursal no encontrada con ID:" + branchId)))
                .map(existingBranch -> existingBranch.withName(newName))
                .flatMap(branchRepository::save);
    }
}
