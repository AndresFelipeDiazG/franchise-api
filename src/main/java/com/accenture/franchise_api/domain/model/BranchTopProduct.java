package com.accenture.franchise_api.domain.model;

public record BranchTopProduct(
        Long branchId,
        String branchName,
        Long productId,
        String productName,
        Integer stock
) {}
