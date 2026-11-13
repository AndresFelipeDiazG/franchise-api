package com.accenture.franchise_api.infrastructure.web.controller;

import com.accenture.franchise_api.application.useCase.AddBranchToFranchiseUseCase;
import com.accenture.franchise_api.application.useCase.UpdateBranchNameUseCase;
import com.accenture.franchise_api.application.useCase.UpdateProductNameUseCase;
import com.accenture.franchise_api.infrastructure.web.dto.BranchResponse;
import com.accenture.franchise_api.infrastructure.web.dto.CreateBranchRequest;
import com.accenture.franchise_api.infrastructure.web.dto.UpdateNameRequest;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/api")
public class BranchController {
     private final AddBranchToFranchiseUseCase addBranchToFranchiseUseCase;
     private final UpdateBranchNameUseCase updateBranchNameUseCase;

    public BranchController(AddBranchToFranchiseUseCase addBranchToFranchiseUseCase,
                            UpdateBranchNameUseCase updateBranchNameUseCase) {
        this.addBranchToFranchiseUseCase = addBranchToFranchiseUseCase;
        this.updateBranchNameUseCase = updateBranchNameUseCase;
    }

    @PostMapping("/franchises/{franchiseId}/branches")
    @ResponseStatus(HttpStatus.CREATED)
    public Mono<BranchResponse> addBranch(
            @PathVariable Long franchiseId,
            @Valid @RequestBody CreateBranchRequest request) {
        return addBranchToFranchiseUseCase.execute(franchiseId, request.name())
                .map(BranchResponse::fromDomain);
    }

    @PatchMapping("/branches/{branchId}/name")
    public Mono<BranchResponse> updateName(
            @PathVariable Long branchId,
            @Valid @RequestBody UpdateNameRequest request
    ) {
        return updateBranchNameUseCase.execute(branchId, request.name())
                .map(BranchResponse::fromDomain);
    }
}
