package com.accenture.franchise_api.infrastructure.web.dto;

import com.accenture.franchise_api.domain.model.Franchise;
import com.accenture.franchise_api.domain.model.Product;

public record ProductResponse(Long id, Long branchId, String name, Integer stock) {
    public static ProductResponse fromDomain(Product product) {
        return new ProductResponse(product.id(), product.branchId(),  product.name(), product.stock());
    }
}
