package com.accenture.franchise_api.infrastructure.web.dto;

import com.accenture.franchise_api.domain.model.BranchTopProduct;

public record BranchTopProductResponse(
        Long branchId,
        String branchName,
        Long productId,
        String productName,
        Integer stock
        ) {
    public static BranchTopProductResponse fromDomain(BranchTopProduct item) {
        return new BranchTopProductResponse(
                item.branchId(),
                item.branchName(),
                item.productId(),
                item.productName(),
                item.stock()
        );
    }
}
