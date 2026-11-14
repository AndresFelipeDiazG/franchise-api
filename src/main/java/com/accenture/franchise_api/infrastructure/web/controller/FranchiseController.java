package com.accenture.franchise_api.infrastructure.web.controller;

import com.accenture.franchise_api.application.useCase.CreateFranchiseUseCase;
import com.accenture.franchise_api.application.useCase.GetTopProductByBranchUseCase;
import com.accenture.franchise_api.application.useCase.UpdateFranchiseNameUseCase;
import com.accenture.franchise_api.infrastructure.web.dto.BranchTopProductResponse;
import com.accenture.franchise_api.infrastructure.web.dto.CreateFranchiseRequest;
import com.accenture.franchise_api.infrastructure.web.dto.FranchiseResponse;
import com.accenture.franchise_api.infrastructure.web.dto.UpdateNameRequest;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/api/franchises")
public class FranchiseController {
    private final CreateFranchiseUseCase createFranchiseUseCase;
    private final UpdateFranchiseNameUseCase updateFranchiseNameUseCase;
    private final GetTopProductByBranchUseCase getTopProductByBranchUseCase;

    public FranchiseController(CreateFranchiseUseCase createFranchiseUseCase,
                               UpdateFranchiseNameUseCase updateFranchiseNameUseCase,
                               GetTopProductByBranchUseCase getTopProductByBranchUseCase) {
        this.createFranchiseUseCase = createFranchiseUseCase;
        this.updateFranchiseNameUseCase = updateFranchiseNameUseCase;
        this.getTopProductByBranchUseCase = getTopProductByBranchUseCase;
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Mono<FranchiseResponse> create(@Valid @RequestBody CreateFranchiseRequest request) {
        return createFranchiseUseCase.execute(request.name())
                .map(FranchiseResponse::fromDomain);
    }

    @PatchMapping("/{franchiseId}/name")
    public Mono<FranchiseResponse> update(
            @PathVariable Long franchiseId,
            @Valid @RequestBody UpdateNameRequest request) {
        return updateFranchiseNameUseCase.execute(franchiseId, request.name() )
                .map(FranchiseResponse::fromDomain);

    }

    @GetMapping("/{franchiseId}/top-products")
    public Flux<BranchTopProductResponse> getTopProductsByBranch(@PathVariable Long franchiseId) {
        return getTopProductByBranchUseCase.execute(franchiseId)
                .map(BranchTopProductResponse::fromDomain);
    }
}
