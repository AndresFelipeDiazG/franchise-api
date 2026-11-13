package com.accenture.franchise_api.infrastructure.web.controller;

import com.accenture.franchise_api.application.useCase.AddProductToBranchUseCase;
import com.accenture.franchise_api.application.useCase.UpdateProductNameUseCase;
import com.accenture.franchise_api.application.useCase.UpdateProductStockUseCase;
import com.accenture.franchise_api.infrastructure.web.dto.CreateProductRequest;
import com.accenture.franchise_api.infrastructure.web.dto.ProductResponse;
import com.accenture.franchise_api.infrastructure.web.dto.UpdateNameRequest;
import com.accenture.franchise_api.infrastructure.web.dto.UpdateStockRequest;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/api")
public class ProductController {
    private final AddProductToBranchUseCase addProductToBranchUseCase;
    private final UpdateProductNameUseCase updateProductNameUseCase;
    private final UpdateProductStockUseCase updateProductStockUseCase;

    public ProductController(AddProductToBranchUseCase addProductToBranchUseCase,
                             UpdateProductNameUseCase updateProductNameUseCase,
                             UpdateProductStockUseCase updateProductStockUseCase) {
        this.addProductToBranchUseCase = addProductToBranchUseCase;
        this.updateProductNameUseCase = updateProductNameUseCase;
        this.updateProductStockUseCase = updateProductStockUseCase;
    }

    @PostMapping("/branches/{branchId}/products")
    @ResponseStatus(HttpStatus.CREATED)
    public Mono<ProductResponse> addProductToBranch(
            @PathVariable Long branchId,
            @Valid @RequestBody CreateProductRequest request
    ) {
        return addProductToBranchUseCase.execute(branchId, request.name(), request.stock())
                .map(ProductResponse::fromDomain);
    }

    @PatchMapping("/products/{productId}/name")
    public Mono<ProductResponse>  updateName(
            @Valid @RequestBody UpdateNameRequest request,
            @PathVariable Long productId) {
        return updateProductNameUseCase.execute(productId, request.name())
                .map(ProductResponse::fromDomain);
    }

    @PatchMapping("/products/{productId}/stock")
    public Mono<ProductResponse>  updateStock(
            @Valid @RequestBody UpdateStockRequest request,
            @PathVariable Long productId) {
        return updateProductStockUseCase.execute(productId, request.stock())
                .map(ProductResponse::fromDomain);
    }
}
